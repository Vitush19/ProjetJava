package com.epf.RentManager.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.dao.ClientDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Client;

public class ClientService {
	
	private static ClientService instance = null;
	private ClientService() {}
	
	public static ClientService getInstance() {
		if(instance == null) {
			instance = new ClientService();
		}
		return instance;
	}
	ClientDao clientdao = ClientDao.getInstance();
	
	public List<Client> findAll() throws ServiceException{
		try {
			return clientdao.findAll();
		}catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public Optional<Client> findById(int id) throws ServiceException{
		Optional<Client> optClient;
		try {
			optClient = clientdao.findById(id);
			if(optClient.isPresent()) {
				Client c = optClient.get();
				System.out.println(c);
			}else {
				System.out.println("Erreur lors du select du client");
			}
			return optClient;
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	public long create(Client client) throws ServiceException {
		checkAge(client);
		checkName(client);
		checkMail(client);
		
		if("".equals(client.getNom().trim())){
			throw new ServiceException("Le nom ne peux pas être vide");
		}
		else if ("".equals(client.getPrenom().trim())) {
			throw new ServiceException("Le prénom ne peux pas être vide");
		}
		try {
			return clientdao.create(client);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public long delete(int id) throws ServiceException{
		try {
			return clientdao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public long update(Client client, String mail) throws ServiceException {
		checkAge(client);
		checkName(client);
		checkMail(client, mail);
		
		if("".equals(client.getNom().trim())){
			throw new ServiceException("Le nom ne peux pas être vide");
		}
		else if ("".equals(client.getPrenom().trim())) {
			throw new ServiceException("Le prénom ne peux pas être vide");
		}
		try {
			return clientdao.update(client);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
		
	}

	private void checkAge(Client client) throws ServiceException {	//verif age
		long age = ChronoUnit.YEARS.between(client.getNaissance().toLocalDate(), LocalDate.now());
		if(age < 18) {
			throw new ServiceException("Le client doit avoir 18 ans il a "+age);
		}
	}
	
	private void checkName(Client client) throws ServiceException {	//verif nom/prenom
		String lastname = client.getNom();
		String firstname = client.getPrenom();
		if (lastname.length() < 2) {
			throw new ServiceException("Le nom doit faire au moins 3 caractères");
		}
		else if (firstname.length() < 2) {
			throw new ServiceException("Le prénom doit faire au moins 3 caractères");
		}
	}
	
	private void checkMail(Client client) throws ServiceException {	//verif mail
		String mail = client.getEmail();
		try {
			List<Client> list = clientdao.findAll();
			for(Client clientbis : list) {
				if(clientbis.getEmail().equals(mail)) {
					throw new ServiceException("Le mail est déjà utilisé.");
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	private void checkMail(Client client, String mail) throws ServiceException {	//surcharge verif mail
		String email = client.getEmail();
		try {
			List<Client> list = clientdao.findAll();
			for(Client clientbis : list) {
				if(clientbis.getEmail().equals(email)) {
					if(clientbis.getEmail().equals(mail)) { //si le mail utilisé est égal à celui qui est modifié, il n'y a pas de pb
						
					}else {
						throw new ServiceException("Le mail est déjà utilisé.");
					}
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	}
	
