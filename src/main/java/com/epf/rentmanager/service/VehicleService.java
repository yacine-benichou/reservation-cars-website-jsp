package com.epf.rentmanager.service;

import java.util.ArrayList; 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.VehiclePlaceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.validator.VehicleValidator;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;

@Service
@Scope("singleton")
public class VehicleService {

	@Autowired
	private VehicleDao vehicleDao;
	
	@Autowired
	private ReservationDao reservationDao;
	
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	public int create(Vehicle vehicle) throws ServiceException, VehiclePlaceException {
		int numberCreated = 0;
		if (!vehicle.getConstructor().isBlank() && !vehicle.getModel().isBlank() && VehicleValidator.isNbSeatsValid(vehicle)) {
			try {
				numberCreated = this.vehicleDao.create(vehicle);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		} else if (!VehicleValidator.isNbSeatsValid(vehicle)) {
			throw new VehiclePlaceException(vehicle.getNbPlace());
		}
		else {
			throw new ServiceException();
		}
		return numberCreated;
	}
	
	public int update(Vehicle vehicle) throws ServiceException, VehiclePlaceException {
		int numberUpdated = 0;
		if (!vehicle.getConstructor().isBlank() && !vehicle.getModel().isBlank() && VehicleValidator.isNbSeatsValid(vehicle)) {
			try {
				numberUpdated = this.vehicleDao.update(vehicle);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		} else if (!VehicleValidator.isNbSeatsValid(vehicle)) {
			throw new VehiclePlaceException(vehicle.getNbPlace());
		} else {
			throw new ServiceException(); 
		}
		return numberUpdated;
	}

	public int delete(Vehicle vehicle) throws ServiceException {
		int numberDeleted = 0;
		int vehicleId = vehicle.getId();
		try {
			List<Reservation> listReservations = reservationDao.findResaByVehicleId(vehicleId);
			for (Reservation reservation : listReservations) {
				reservationDao.delete(reservation);
			}
			numberDeleted = this.vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return numberDeleted;
	}

	public Vehicle findById(int id) throws ServiceException {
		Vehicle vehicle = new Vehicle();
		try {
			vehicle = this.vehicleDao.findById(id).get();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return vehicle;
	}

	public List<Vehicle> findAll() throws ServiceException {
		List<Vehicle> vehicleList = new ArrayList<>();
		try {
			vehicleList = this.vehicleDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return vehicleList;
	}
	
	public int count() throws ServiceException {
		int nbVehicle = 0;
		try {
			nbVehicle = this.vehicleDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return nbVehicle;
	}
	
	public List<Vehicle> findVehicleByClientId(int clientId) throws ServiceException {
		List<Vehicle> vehicleList = new ArrayList<>();
		try {
			vehicleList = this.vehicleDao.findVehicleByClientId(clientId);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return vehicleList;
	}
	
}