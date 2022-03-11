package epf;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.epf.rentmanager.configuration.TestConfiguration;
import com.epf.rentmanager.exception.ReservationDateException;
import com.epf.rentmanager.exception.ReservationPossibleException;
import com.epf.rentmanager.exception.ReservationTimeByOneUserException;
import com.epf.rentmanager.exception.ReservationTimeForOneVehicleException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = {TestConfiguration.class})
public class ReservationServiceTest {
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	VehicleService vehicleService;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Test
	public void shouldCreateReservation() throws ServiceException, ReservationDateException, ReservationPossibleException, ReservationTimeByOneUserException, ReservationTimeForOneVehicleException {
		LocalDate dateStart = LocalDate.parse("11/03/2022", formatter);
		LocalDate dateEnd = LocalDate.parse("18/03/2022", formatter);
		
		Reservation reservation = new Reservation(2, 1, 3, dateStart, dateEnd);
		assertTrue("La réservation n'a pas été créée !", reservationService.create(reservation) > 0);
	}
	
	@Test
	public void shouldFindReservationById() throws ServiceException {
		LocalDate dateStart = LocalDate.parse("11/03/2022", formatter);
		LocalDate dateEnd = LocalDate.parse("16/03/2022", formatter);
		Reservation reservation = new Reservation(1, 2, 2, dateStart, dateEnd);
		Reservation reservationFound = reservationService.findResaById(reservation.getId());
		boolean areReservationsEquals = reservationFound.getClientId() == reservation.getClientId() 
				&& reservationFound.getVehicleId() == reservation.getVehicleId() && reservationFound.getDateStart().equals(reservation.getDateStart())
				&& reservationFound.getDateEnd().equals(reservation.getDateEnd());
		
		assertTrue("La réservation n'a pas été trouvé !", areReservationsEquals);
	}
	
	@Test
	public void isListReservationFoundByClientIdNotNull() throws ServiceException {
		assertNotEquals(null, reservationService.findResaByClientId(clientService.findById(2).getId()));
	}
	
	@Test
	public void isListReservationFoundByClientIdNotEmpty() throws ServiceException {
		String errorMessage = "La liste des réservations trouvé par l'id du client " + clientService.findById(2).getFirstname() + " " + clientService.findById(2).getLastname() + " est vide !";
		assertTrue(errorMessage, !reservationService.findResaByClientId(clientService.findById(2).getId()).isEmpty());
	}
	
	@Test
	public void isListReservationFoundByVehicleIdNotNull() throws ServiceException {
		assertNotEquals(null, reservationService.findResaByVehicleId(2));
	}
	
	@Test
	public void isListReservationFoundByVehicleIdNotEmpty() throws ServiceException {
		String errorMessage = "La liste des réservations trouvée par l'id du véhicule " + vehicleService.findById(2).getConstructor() + " " + vehicleService.findById(2).getModel() + " est vide !";
		assertTrue(errorMessage, !reservationService.findResaByVehicleId(2).isEmpty());
	}
	
	@Test
	public void isListClientIdFoundByVehicleIdNotNull() throws ServiceException {
		assertNotEquals(null, reservationService.findClientIdByVehicleId(2));
	}
	
	@Test
	public void isListClientIdFoundByVehicleIdNotEmpty() throws ServiceException {
		String errorMessage = "La liste des ids des clients trouvée par l'id du véhicule " + vehicleService.findById(2).getConstructor() + " " + vehicleService.findById(2).getModel() + " est vide !";
		assertTrue(errorMessage, !reservationService.findClientIdByVehicleId(2).isEmpty());
	}
	
	@Test
	public void isListReservationNotNull() throws ServiceException {
		assertNotEquals(null, reservationService.findAll());
	}
	
	@Test
	public void isListReservationNotEmpty() throws ServiceException {
		assertTrue("La liste des réservations est vide !", !reservationService.findAll().isEmpty());
	}
	
	@Test
	public void shouldReservationCountGreaterThan0() throws ServiceException {
		assertTrue("le nombre de réservations est égal à 0 !", reservationService.count() > 0);
	}
	
	@Test
	public void shouldUpdateReservation() throws ServiceException, ReservationDateException, ReservationPossibleException, ReservationTimeByOneUserException, ReservationTimeForOneVehicleException {
		LocalDate dateStart = LocalDate.parse("17/04/2022", formatter);
		LocalDate dateEnd = LocalDate.parse("22/04/2022", formatter);
		
		Reservation reservation = new Reservation(1, 2, 2, dateStart, dateEnd);
		assertTrue("La réservation n'a pas été modifié !", reservationService.update(reservation) > 0);
	}
	
	@Test
	public void shouldDeleteReservation() throws ServiceException {
		LocalDate dateStart = LocalDate.parse("11/03/2022", formatter);
		LocalDate dateEnd = LocalDate.parse("18/03/2022", formatter);
		
		Reservation reservation = new Reservation(2, 1, 3, dateStart, dateEnd);
		assertTrue("La réservation n'a pas été supprimé !", reservationService.delete(reservation) > 0);
	}
}
