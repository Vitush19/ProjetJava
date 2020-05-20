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
import com.ensta.rentmanager.model.Reservation;
import com.ensta.rentmanager.model.Vehicle;
import com.ensta.rentmanager.persistence.ConnectionManager;
import com.epf.RentManager.service.ReservationService;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id = ?";
	
	public long update(Vehicle vehicle) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(UPDATE_VEHICLE_QUERY);) 
			{
				statement.setInt(4, vehicle.getId());
				statement.setString(1, vehicle.getConstructeur());
				statement.setString(2, vehicle.getModele());
				statement.setInt(3, vehicle.getNb_places());
				long result = statement.executeUpdate();
				statement.close();
				conn.close();
				return result;
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new DaoException("Erreur lors de la création : "+e.getMessage());
			}
		
	}
	
	public long create(Vehicle vehicle) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(CREATE_VEHICLE_QUERY);) 
			{
				statement.setString(1, vehicle.getConstructeur());
				statement.setString(2, vehicle.getModele());
				statement.setInt(3, vehicle.getNb_places());
				long result = statement.executeUpdate();
				statement.close();
				conn.close();
				return result;
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new DaoException("Erreur lors de la création : "+e.getMessage());
			}
		
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(DELETE_VEHICLE_QUERY);) 
			{
				statement.setInt(1, vehicle.getId());
				long result = statement.executeUpdate();
				statement.close();
				conn.close();
				return result;
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new DaoException("Erreur lors de la suppression : "+e.getMessage());
			}
		
		
	}

	public Optional<Vehicle> findById(int id) throws DaoException {
		Vehicle v = new Vehicle();
		Optional<Vehicle> optVehicle = Optional.of(v);
		try ( Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(FIND_VEHICLE_QUERY);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				v = new Vehicle(id,
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getInt(4));
				optVehicle = Optional.of(v);
			}
			resultSet.close();
		}catch (SQLException e) {
			return Optional.empty();
		}
		return optVehicle;
	}

	public List<Vehicle> findAll() throws DaoException {
		List <Vehicle> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(FIND_VEHICLES_QUERY);)
		{
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Vehicle vehicle = new Vehicle (resultSet.getInt(1),
											   resultSet.getString(2),
											   resultSet.getString(3),
											   resultSet.getInt(4));
				resultList.add(vehicle);
			}
			return resultList;
		}catch (SQLException e) {
			throw new DaoException("Erreur lors de la création : "+e.getMessage());
		}
	}
	
	public long delete(int id) throws DaoException {
		
		List<Reservation> resultlist = new ArrayList<>();
		ReservationService reservationService = ReservationService.getInstance();
		
		try (Connection conn = ConnectionManager.getConnection();
				 PreparedStatement statement = conn.prepareStatement(DELETE_VEHICLE_QUERY);) 
			{
				statement.setInt(1, id);
				try {
					resultlist = reservationService.findByVehicleId(id);
					try {
						for(Reservation r : resultlist) {
							reservationService.delete(r.getId());
						}
						long result = statement.executeUpdate();
						statement.close();
						conn.close();
						return result;
					} catch (ServiceException e) {
						throw new DaoException("Erreur lors de la suppression" + e.getMessage());
					}
				}catch(ServiceException e) {
					throw new DaoException(
							"Erreur lors de la récupération de la liste de réservation du véhicule" 
				+ e.getMessage());
				}
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new DaoException("Erreur lors de la suppresion : "+e.getMessage());
			}
	}
	
	public static void main (String... args) {
		VehicleDao dao = VehicleDao.getInstance();
		try {
			List<Vehicle> list = dao.findAll();
			
			for(Vehicle v : list ) {
				System.out.println(v);
			}
		} catch (DaoException e) {
			System.out.println("Erreur lors du Select ALL : " + e.getMessage());
		}
	}
}
