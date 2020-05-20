package com.ensta.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.model.Reservation;
import com.ensta.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin = ? WHERE id = ?";
	
	public long update(Reservation reservation) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(UPDATE_RESERVATION_QUERY);) 
			{
				statement.setInt(5, reservation.getId());
				statement.setInt(1, reservation.getClient_id());
				statement.setInt(2, reservation.getVehicle_id());
				statement.setDate(3, reservation.getDebut());
				statement.setDate(4, reservation.getFin());
				
				long result = statement.executeUpdate();
				statement.close();
				conn.close();
				
				return result;
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new DaoException("Erreur lors de la création : "+e.getMessage());
			}
	}
	
	public long create(Reservation reservation) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(CREATE_RESERVATION_QUERY);) 
			{
				statement.setInt(1, reservation.getClient_id());
				statement.setInt(2, reservation.getVehicle_id());
				statement.setDate(3, reservation.getDebut());
				statement.setDate(4, reservation.getFin());
				
				long result = statement.executeUpdate();
				statement.close();
				conn.close();
				
				return result;
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new DaoException("Erreur lors de la création : "+e.getMessage());
			}
	}
	
	public long delete(int reservation) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(DELETE_RESERVATION_QUERY);) 
			{
				statement.setInt(1, reservation);
				
				long result = statement.executeUpdate();
				statement.close();
				conn.close();
				
				return result;
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new DaoException("Erreur lors de la création : "+e.getMessage());
			}
	}

	public List<Reservation> findResaByClientId(int clientId) throws DaoException {
		
		List<Reservation> list = new ArrayList<>();
		
		try ( Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY );) {
			
			statement.setInt(1, clientId);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Reservation r = new Reservation(resultSet.getInt(1),
						clientId,
						resultSet.getInt(2),
						resultSet.getDate(3),
						resultSet.getDate(4));
				list.add(r);
			}
			resultSet.close();
			return list;
			
		}catch (SQLException e) {
			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}
	}
	
	public List<Reservation> findResaByVehicleId(int vehicleId) throws DaoException {

		List<Reservation> list = new ArrayList<>();
		
		try ( Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY );) {
			
			statement.setInt(1, vehicleId);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Reservation r = new Reservation(resultSet.getInt(1),
						vehicleId,
						resultSet.getInt(2),
						resultSet.getDate(3),
						resultSet.getDate(4));
				list.add(r);
			}
			resultSet.close();
			return list;
			
		}catch (SQLException e) {
			throw new DaoException("erreur lors de la connection : " + e.getMessage());
		}
	}

	public List<Reservation> findAll() throws DaoException {
		List <Reservation> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(FIND_RESERVATIONS_QUERY);)
		{
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Reservation reservation = new Reservation(resultSet.getInt(1),
						resultSet.getInt(2),
						resultSet.getInt(3),
						resultSet.getDate(4),
						resultSet.getDate(5));
				resultList.add(reservation);
			}
			
			return resultList;
		}catch (SQLException e) {
			throw new DaoException("Erreur lors de la création : "+e.getMessage());
		}
		 
	}
	
	public static void main (String... args) {
		ReservationDao dao = ReservationDao.getInstance();
		
		try {
			List<Reservation> list = dao.findAll();
			for(Reservation r : list ) {
				System.out.println(r);
			}
		} catch (DaoException e) {
			System.out.println("Erreur lors du Select ALL : " + e.getMessage());
		}
	}
}
