package com.ensta.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Client;
import com.ensta.rentmanager.model.Reservation;
import com.ensta.rentmanager.persistence.ConnectionManager;
import com.epf.RentManager.service.ReservationService;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id = ?";
	
	public long update(Client client) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(UPDATE_CLIENT_QUERY);) 
			{
				statement.setInt(5, client.getId());
				statement.setString(1, client.getNom());
				statement.setString(2, client.getPrenom());
				statement.setString(3, client.getEmail());
				statement.setDate(4, client.getNaissance());
				long result = statement.executeUpdate();
				statement.close();
				conn.close();
				return result;
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new DaoException("Erreur lors de la création : "+e.getMessage());
			}
	}
	
	public long create(Client client) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement statement = conn.prepareStatement(CREATE_CLIENT_QUERY);) 
		{
			statement.setString(1, client.getNom());
			statement.setString(2, client.getPrenom());
			statement.setString(3, client.getEmail());
			statement.setDate(4, client.getNaissance());
			long result = statement.executeUpdate();
			statement.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DaoException("Erreur lors de la création : "+e.getMessage());
		}
	}
	
	public long delete(int id) throws DaoException {
		
		List<Reservation> resultlist = new ArrayList<>();
		ReservationService reservationService = ReservationService.getInstance();
		
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(DELETE_CLIENT_QUERY);) 
			{
				statement.setInt(1, id);
				try {
					resultlist = reservationService.findByClientId(id);
					try {
						for(Reservation r : resultlist) {
							reservationService.delete(r.getId());
						}
						long result = statement.executeUpdate();
						statement.close();
						conn.close();
						return result;
					}catch (ServiceException e) {
						throw new DaoException("Erreur lors de suppression "
								+ e.getMessage());
					}
				}catch (ServiceException e) {
					throw new DaoException(
							"Erreur lors de la récupération de la liste de réservations du client"
					+ e.getMessage());
				}
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new DaoException("Erreur lors de la suppresion : "+e.getMessage());
			}
	}

	public Optional<Client> findById(int id) throws DaoException {
		
		Client c = new Client();
		Optional<Client> optClient = Optional.of(c);
		try ( Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(FIND_CLIENT_QUERY);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				c = new Client(id,
						resultSet.getString(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getDate(4));
				optClient = Optional.of(c);
			}
		}catch (SQLException e) {
			return Optional.empty();
		}
		return optClient;
	}

	public List<Client> findAll() throws DaoException {
		List <Client> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(FIND_CLIENTS_QUERY);)
		{
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Client client = new Client (resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getString(4),
						resultSet.getDate(5));
				resultList.add(client);
			}
			return resultList;
		}catch (SQLException e) {
			throw new DaoException("Erreur lors de la création : "+e.getMessage());
		}
	}
	
	public static void main (String... args) {
		ClientDao dao = ClientDao.getInstance();
		try {
			List<Client> list = dao.findAll();
			for(Client c : list ) {
				System.out.println(c);
			}
		} catch (DaoException e) {
			System.out.println("Erreur lors du Select ALL : " + e.getMessage());
		}
	}

}
