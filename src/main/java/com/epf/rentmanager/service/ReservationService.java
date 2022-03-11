package com.epf.rentmanager.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ReservationDateException;
import com.epf.rentmanager.exception.ReservationPossibleException;
import com.epf.rentmanager.exception.ReservationTimeByOneUserException;
import com.epf.rentmanager.exception.ReservationTimeForOneVehicleException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.validator.ReservationValidator;

@Service
@Scope("singleton")
public class ReservationService {
	
	@Autowired
	private ReservationDao reservationDao;

	private ReservationService(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	public int create(Reservation reservation) throws ServiceException, ReservationDateException, ReservationPossibleException, ReservationTimeByOneUserException, ReservationTimeForOneVehicleException {
		int numberCreated = 0;
		List<Reservation> listOfRentsByClientId = new ArrayList<>();
		List<Reservation> listOfRentsByVehicleId = new ArrayList<>();
		int clientId = reservation.getClientId();
		int vehicleId = reservation.getVehicleId();
		try {
			listOfRentsByClientId = reservationDao.findResaByClientId(clientId);
			listOfRentsByVehicleId = reservationDao.findResaByVehicleId(vehicleId);
		} catch (DaoException e) {
			e.printStackTrace();
		}

		if (ReservationValidator.startDateIsValid(reservation) & ReservationValidator.endDateIsValid(reservation)
				& ReservationValidator.rentalIsPossible(reservation, listOfRentsByClientId)
				& ReservationValidator.rentalIsPossible(reservation, listOfRentsByVehicleId)
				& ReservationValidator.maxRentalTimeOfVehicleByOneUser(reservation, listOfRentsByClientId)
				& ReservationValidator.maxRentalTimeForOneVehicle(reservation, listOfRentsByVehicleId)) {
			try {
				numberCreated = this.reservationDao.create(reservation);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		} else if (!ReservationValidator.startDateIsValid(reservation) || !ReservationValidator.endDateIsValid(reservation)) {
			throw new ReservationDateException(reservation.getDateStart().plus(8, ChronoUnit.DAYS));
		} else if (!ReservationValidator.rentalIsPossible(reservation, listOfRentsByClientId)
				|| !ReservationValidator.rentalIsPossible(reservation, listOfRentsByVehicleId)) {
			throw new ReservationPossibleException(reservation.getDateStart(), reservation.getDateEnd());
		} else if (ReservationValidator.maxRentalTimeOfVehicleByOneUser(reservation, listOfRentsByClientId)) {
			throw new ReservationTimeByOneUserException(reservation.getClient(), reservation.getVehicle());
		} else {
			throw new ReservationTimeForOneVehicleException(reservation.getVehicle());
		}

		return numberCreated;
	}
	
	public int update(Reservation reservation) throws ReservationDateException, ReservationPossibleException, ReservationTimeByOneUserException, ReservationTimeForOneVehicleException {
		int numberUpdated = 0;
		List<Reservation> listOfRentsByClientId = new ArrayList<>();
		List<Reservation> listOfRentsByVehicleId = new ArrayList<>();
		int clientId = reservation.getClientId();
		int vehicleId = reservation.getVehicleId();
		try {
			listOfRentsByClientId = reservationDao.findResaByClientId(clientId);
			listOfRentsByVehicleId = reservationDao.findResaByVehicleId(vehicleId);
		} catch (DaoException e) {
			e.printStackTrace();
		}

		if (ReservationValidator.startDateIsValid(reservation) & ReservationValidator.endDateIsValid(reservation)
				& ReservationValidator.rentalIsPossible(reservation, listOfRentsByClientId)
				& ReservationValidator.rentalIsPossible(reservation, listOfRentsByVehicleId)
				& ReservationValidator.maxRentalTimeOfVehicleByOneUser(reservation, listOfRentsByClientId)
				& ReservationValidator.maxRentalTimeForOneVehicle(reservation, listOfRentsByVehicleId)) {
			try {
				numberUpdated = this.reservationDao.update(reservation);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		} else if (!ReservationValidator.startDateIsValid(reservation) || !ReservationValidator.endDateIsValid(reservation)) {
			throw new ReservationDateException(reservation.getDateStart().plus(8, ChronoUnit.DAYS));
		} else if (!ReservationValidator.rentalIsPossible(reservation, listOfRentsByClientId)
				|| !ReservationValidator.rentalIsPossible(reservation, listOfRentsByVehicleId)) {
			throw new ReservationPossibleException(reservation.getDateStart(), reservation.getDateEnd());
		} else if (ReservationValidator.maxRentalTimeOfVehicleByOneUser(reservation, listOfRentsByClientId)) {
			throw new ReservationTimeByOneUserException(reservation.getClient(), reservation.getVehicle());
		} else {
			throw new ReservationTimeForOneVehicleException(reservation.getVehicle());
		}

		return numberUpdated;
	}

	public int delete(Reservation reservation) throws ServiceException {
		int numberDeleted = 0;
		try {
			numberDeleted = this.reservationDao.delete(reservation);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return numberDeleted;
	}
	
	public Reservation findResaById(int reservationId) throws ServiceException {
		Reservation reservation = new Reservation();
		try {
			 reservation = this.reservationDao.findResaById(reservationId).get();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return reservation;
	}

	public List<Reservation> findResaByClientId(int clientId) throws ServiceException {
		List<Reservation> reservationList = new ArrayList<>();
		try {
			reservationList = this.reservationDao.findResaByClientId(clientId);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return reservationList;
	}

	public List<Reservation> findResaByVehicleId(int vehicleId) throws ServiceException {
		List<Reservation> reservationList = new ArrayList<>();
		try {
			reservationList = this.reservationDao.findResaByVehicleId(vehicleId);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return reservationList;
	}
	
	public List<Integer> findClientIdByVehicleId(int vehicleId) throws ServiceException {
		List<Integer> clientIdList = new ArrayList<>();
		try {
			clientIdList = this.reservationDao.findClientIdByVehicleId(vehicleId);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return clientIdList;
	}

	public List<Reservation> findAll() throws ServiceException {
		List<Reservation> reservationList = new ArrayList<>();
		try {
			reservationList = this.reservationDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return reservationList;
	}

	public int count() throws ServiceException {
		int nbResa = 0;
		try {
			nbResa = this.reservationDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return nbResa;
	}

}
