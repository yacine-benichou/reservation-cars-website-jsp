package com.epf.rentmanager.main;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ReservationDateException;
import com.epf.rentmanager.exception.ReservationPossibleException;
import com.epf.rentmanager.exception.ReservationTimeByOneUserException;
import com.epf.rentmanager.exception.ReservationTimeForOneVehicleException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.VehiclePlaceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	

	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) throws ServiceException, DaoException, VehiclePlaceException, ReservationDateException, ReservationPossibleException, ReservationTimeByOneUserException, ReservationTimeForOneVehicleException {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		ClientService clientService = context.getBean(ClientService.class);
		ClientDao clientDao = context.getBean(ClientDao.class);
		VehicleService vehicleService = context.getBean(VehicleService.class);
		ReservationService reservationService = context.getBean(ReservationService.class);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateStart = LocalDate.parse("11/03/2022", formatter);
		LocalDate dateEnd = LocalDate.parse("18/03/2022", formatter);
		
		Reservation reservation = new Reservation(2, 1, 3, dateStart, dateEnd);
		reservationService.create(reservation);
		
	}
	
}
