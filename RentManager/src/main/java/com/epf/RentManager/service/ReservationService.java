package com.epf.RentManager.service;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ensta.rentmanager.dao.ReservationDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Client;
import com.ensta.rentmanager.model.Reservation;
import com.ensta.rentmanager.model.Vehicle;

public class ReservationService {
	private static ReservationService instance = null;
	private ReservationService() {}
	
	public static ReservationService getInstance() {
		if(instance == null) {
			instance = new ReservationService();
		}
		return instance;
	}
	ReservationDao reservationdao = ReservationDao.getInstance();
	
	public List<Reservation> findAll() throws ServiceException{
		try {
			return reservationdao.findAll();
		}catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public long create(Reservation reservation) throws ServiceException {
		checkUserReservation(reservation);
		checkVehicleReservation(reservation);
		checkUpDurationReservation(reservation);
		try {
			return reservationdao.create(reservation);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public long delete(int id) throws ServiceException{
		try {
			return reservationdao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public long update(Reservation reservation, Date debut, Date fin) throws ServiceException {
		checkUserReservation(reservation);
		checkVehicleReservation(reservation, debut, fin);
		checkUpDurationReservation(reservation);
		try {
			return reservationdao.update(reservation);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public List<Reservation> findByClientId(int id) throws ServiceException{
		List<Reservation> list = new ArrayList<>();
		try {
			list = reservationdao.findResaByClientId(id);
			return list;
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	public List<Reservation> findByVehicleId(int id) throws ServiceException{
		List<Reservation> list = new ArrayList<>();
		try {
			list = reservationdao.findResaByVehicleId(id);
			return list;
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	public int findNumber(Client client) throws ServiceException{	//récupérer nombre de réservation d'un client
		int count = 0;
		try {
			List<Reservation> list = reservationdao.findAll();
			for(Reservation rent : list) {
				if(rent.getClient_id() == client.getId()) {
					count++;
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int findReservationNumber(Vehicle vehicle) throws ServiceException{	//récupérer nombre de réservation d'un véhicule
		int count = 0;
		try {
			List<Reservation> list = reservationdao.findAll();
			for(Reservation rent : list) {
				if(rent.getVehicle_id() == vehicle.getId()) {
					count++;
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	private void checkVehicleReservation(Reservation reservation) throws ServiceException {	//vérification cohérence date de réservation 
		int vehicle_id = reservation.getVehicle_id();
		Date debut = reservation.getDebut();
		Date fin = reservation.getFin();
		
		List<Reservation> list;
		try {
			list = reservationdao.findAll();
			for(Reservation rent : list) {
				if(vehicle_id == rent.getVehicle_id()) {	//si véhicule déjà réservée
					if(debut.equals(rent.getDebut()) || debut.after(rent.getDebut()) && debut.before(rent.getFin()) 
							|| debut.equals(rent.getFin()) || fin.equals(rent.getDebut()) 
							|| fin.after(rent.getDebut()) && fin.before(rent.getFin()) 
							|| fin.equals(rent.getFin())) {	//vérification d'imbrication ou égalité de dates
						throw new ServiceException("Le véhicule est déjà réservé.");
					}
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	private void checkVehicleReservation(Reservation reservation, Date start, Date end) throws ServiceException {	//surcharge vérification cohérence date de réservation
		int vehicle_id = reservation.getVehicle_id();
		Date debut = reservation.getDebut();
		Date fin = reservation.getFin();
		
		List<Reservation> list;
		try {
			list = reservationdao.findAll();
			for(Reservation rent : list) {
				if(vehicle_id == rent.getVehicle_id()) {	//si véhicule déjà réservée
					if(debut.equals(rent.getDebut()) || debut.after(rent.getDebut()) && debut.before(rent.getFin()) 
							|| debut.equals(rent.getFin()) || fin.equals(rent.getDebut()) 
							|| fin.after(rent.getDebut()) && fin.before(rent.getFin()) 
							|| fin.equals(rent.getFin())) {	//vérification d'imbrication ou égalité de dates
						if(debut.equals(start) || fin.equals(end)) {	//si les dates modifiées sont égales à celles non modifiées -> il n'y a pas d'erreur
							
						}else {
							throw new ServiceException("Le véhicule est déjà réservé.");
						}
					}
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	private void checkUpDurationReservation(Reservation reservation) throws ServiceException {	//verification réservation plus de 30jours
		int vehicle_id = reservation.getVehicle_id();
		Date debut = reservation.getDebut();
		Date fin = reservation.getFin();
		int j = (int) ChronoUnit.DAYS.between(debut.toLocalDate(), fin.toLocalDate());
		int i = 0;
		int count = 1;
		List<Reservation> list;
		try {
			list = reservationdao.findResaByVehicleId(vehicle_id); //liste réservation du véhicule en question
			while (count == 1) {
				count = 0;
				for(Reservation resa : list) {
					while(ChronoUnit.DAYS.between(resa.getFin().toLocalDate(), debut.toLocalDate()) == 1) {	//1 ere boucle pr vérif si il y a une reservation dont la date fin correspond à la veille du début de la nouvelle réservation 
						for(Reservation rent : list) {
							if(ChronoUnit.DAYS.between(rent.getFin().toLocalDate(), debut.toLocalDate()) == 1) { //si c'est le cas on force à nouveau le bouclage et on récupère le nb de jours déjà loué
								debut = rent.getDebut();
								i = i + (int) ChronoUnit.DAYS.between(rent.getDebut().toLocalDate(), rent.getFin().toLocalDate());
								count = 1;
							}
						}
					}
					while(ChronoUnit.DAYS.between(fin.toLocalDate(), resa.getDebut().toLocalDate()) == 1) {	//2 eme boucle pr vérif si il y a une reservation dont la date debut correspond au lendemain de la fin de la nouvelle réservation
						for(Reservation rent : list) {
							if(ChronoUnit.DAYS.between(fin.toLocalDate(), rent.getDebut().toLocalDate()) == 1) {	//si c'est le cas on force à nouveau le bouclage et on récupère le nb de jours déjà loué
								debut = rent.getFin();
								i = i + (int) ChronoUnit.DAYS.between(rent.getDebut().toLocalDate(), rent.getFin().toLocalDate());
								count = 1;
							}
						}
					}
				}
			}
			if (i+j > 30) {
				throw new ServiceException("Le véhicule ne peut plus être réservé");
			}			
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	private void checkUserReservation(Reservation reservation) throws ServiceException { //verification si client loue plus de 7j ou moins de 1j
		Date debut = reservation.getDebut();
		Date fin = reservation.getFin();
		long diffInMillies = fin.getTime() - debut.getTime();
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		if(diff > 7) {
			throw new ServiceException("Vous ne pouvez pas réserver plus de 7 jours");
		}
		if (diff <= 0) {
			throw new ServiceException("Vous devez réserver pour un jour au minimum");
		}
	}
}
