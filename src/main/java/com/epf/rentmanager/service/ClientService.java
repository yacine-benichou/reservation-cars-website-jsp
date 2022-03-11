package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.AgeException;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.EmailException;
import com.epf.rentmanager.exception.FirstNameException;
import com.epf.rentmanager.exception.LastNameException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.validator.ClientValidator;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;

@Service
@Scope("singleton")
public class ClientService {
	
	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private ReservationDao reservationDao;
	

	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public int create(Client client) throws ServiceException, EmailException, AgeException, FirstNameException, LastNameException {
		int numberCreated = 0;
		client.setFirstname(client.getFirstname().trim());
		client.setLastname(client.getLastname().trim());
		
		if (!client.getFirstname().isBlank() || !client.getLastname().isBlank()) {
				try {
					boolean isLegal = ClientValidator.isLegal(client);
					boolean isEmailTaken = ClientValidator.isEmailNotTaken(client, this.clientDao.findAll());
					boolean isFirstNameValid = ClientValidator.isFirstNameValid(client);
					boolean isLastNameValid = ClientValidator.isLastNameValid(client);
					if (isFirstNameValid && isLastNameValid) {
						
						if (isLegal && isEmailTaken) {
							numberCreated = this.clientDao.create(client);
						} 
						else if (!isLegal) {
							throw new AgeException(client.getLastname() + " " + client.getFirstname());
						} 
						else {
							throw new EmailException(client.getEmail());
						}
						
					} 
					else if (!isFirstNameValid) {
						throw new FirstNameException(client.getFirstname(), client.getId());
					} 
					else {
						throw new LastNameException(client.getLastname(), client.getId());
					}
					
				}  catch (DaoException e) {
						e.printStackTrace();
					}	
			} else {
			throw new ServiceException();
		}
		return numberCreated;
	}
	
	public int update(Client client) throws ServiceException, AgeException, EmailException, FirstNameException, LastNameException {
		int numberUpdated = 0;
		client.setFirstname(client.getFirstname().trim());
		client.setLastname(client.getLastname().trim());
		
		if (!client.getFirstname().isBlank() || !client.getLastname().isBlank()) {
				try {
					boolean isLegal = ClientValidator.isLegal(client);
					boolean isFirstNameValid = ClientValidator.isFirstNameValid(client);
					boolean isLastNameValid = ClientValidator.isLastNameValid(client);
					if (isFirstNameValid && isLastNameValid) {
						
						if (isLegal) {
							numberUpdated = this.clientDao.update(client);
						} 
						else {
							throw new AgeException(client.getLastname() + " " + client.getFirstname());
						} 
						
					} 
					else if (!isFirstNameValid) {
						throw new FirstNameException(client.getFirstname(), client.getId());
					} 
					else {
						throw new LastNameException(client.getLastname(), client.getId());
					}
					
				}  catch (DaoException e) {
						e.printStackTrace();
					}	
			} else {
			throw new ServiceException();
		}
		return numberUpdated;
		
	}

	public int delete(Client client) throws ServiceException {
		int numberDeleted = 0;
		try {
			List<Reservation> listReservations = reservationDao.findResaByClientId(client.getId());
			for (Reservation reservation : listReservations) {
				reservationDao.delete(reservation);
			}
			numberDeleted = this.clientDao.delete(client);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return numberDeleted;
	}

	public Client findById(int id) throws ServiceException {
		Client client = new Client();
		try {
			client = this.clientDao.findById(id).get();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return client;
	}

	public List<Client> findAll() throws ServiceException {
		List<Client> clientList = new ArrayList<>();
		try {
			clientList = this.clientDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return clientList;
	}

	public int count() throws ServiceException {
		int nbClient = 0;
		try {
			nbClient = this.clientDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return nbClient;
	}

}