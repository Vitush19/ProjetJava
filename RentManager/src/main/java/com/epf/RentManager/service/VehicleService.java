package com.epf.RentManager.service;

import java.util.List;
import java.util.Optional;
import com.ensta.rentmanager.dao.VehicleDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Vehicle;

public class VehicleService {
	private static VehicleService instance = null;
	private VehicleService() {};
	
	public static VehicleService getInstance() {
		if(instance == null) {
			instance = new VehicleService();
		}
		return instance;
	}
	
	VehicleDao vehicledao = VehicleDao.getInstance();
		
		public List<Vehicle> findAll() throws ServiceException{
			try {
				return vehicledao.findAll();
			}catch (DaoException e) {
				throw new ServiceException(e.getMessage());
			}
		}
		
		public Optional<Vehicle> findById(int id) throws ServiceException{
			Optional<Vehicle> optVehicle;
			try {
				optVehicle = vehicledao.findById(id);
				if(optVehicle.isPresent()) {
					Vehicle v = optVehicle.get();
					System.out.println(v);
				}else {
					System.out.println("Erreur lors du select du vehicle");
				}
			} catch (DaoException e) {
				throw new ServiceException();
			}
			return optVehicle;
		}
		
	public long create(Vehicle vehicle) throws ServiceException {
		checkVehicle(vehicle);
		try {
			return vehicledao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public long update(Vehicle vehicle) throws ServiceException {
		checkVehicle(vehicle);
		try {
			return vehicledao.update(vehicle);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public long delete(int id) throws ServiceException{
		try {
			return vehicledao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	private void checkVehicle(Vehicle vehicle) throws ServiceException {	//verif constructeur/modele/nb de places du véhicule
		int nb_places = vehicle.getNb_places();
		String constructeur = vehicle.getConstructeur();
		String modele = vehicle.getModele();
		
		if(nb_places < 2 || nb_places > 9) {
			throw new ServiceException("Veuillez renseigner un nombre de places compris entre 2 et 9");
		}else if (constructeur.length() == 0) {
			throw new ServiceException("Veuillez renseigner un constructeur pour le véhicule");
		}else if (modele.length() == 0) {
			throw new ServiceException("Veuillez renseigner un modèle pour le véhicule");
		}
	}
	
}
