package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.exception.ReservationDateException;
import com.epf.rentmanager.exception.ReservationPossibleException;
import com.epf.rentmanager.exception.ReservationTimeByOneUserException;
import com.epf.rentmanager.exception.ReservationTimeForOneVehicleException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.validator.ReservationValidator;

@WebServlet(urlPatterns = "/rents/create")
public class ReservationCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	VehicleService vehicleService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Vehicle> vehicles = new ArrayList<>();
		List<Client> clients = new ArrayList<>();
		LocalDate minStartDate = LocalDate.now();
		LocalDate maxStartDate = LocalDate.now().plus(6, ChronoUnit.MONTHS);
		request.setAttribute("minStartDate", minStartDate);
		request.setAttribute("maxStartDate", maxStartDate);
		try {
			vehicles = vehicleService.findAll();
			clients = clientService.findAll();
			request.setAttribute("listClients", clients);
			request.setAttribute("listVehicles", vehicles);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
			dispatcher.forward(request, response);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		int clientId = Integer.parseInt(request.getParameterValues("client")[0]);
		int vehicleId = Integer.parseInt(request.getParameterValues("car")[0]);
		
		List<Reservation> listOfRentsByClientId = new ArrayList<>();
		List<Reservation> listOfRentsByVehicleId = new ArrayList<>();
		try {
			listOfRentsByClientId = reservationService.findResaByClientId(clientId);
			listOfRentsByVehicleId = reservationService.findResaByVehicleId(vehicleId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		String startDateString = request.getParameter("begin");
		LocalDate startDate = LocalDate.parse(startDateString, formatter);

		String endDateString = request.getParameter("end");
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		Reservation reservation = new Reservation(0, clientId, vehicleId, startDate, endDate);
		try {
			if (ReservationValidator.startDateIsValid(reservation) & ReservationValidator.endDateIsValid(reservation)
					& ReservationValidator.rentalIsPossible(reservation, listOfRentsByClientId)
					& ReservationValidator.rentalIsPossible(reservation, listOfRentsByVehicleId)
					& ReservationValidator.maxRentalTimeOfVehicleByOneUser(reservation, listOfRentsByClientId)
					& ReservationValidator.maxRentalTimeForOneVehicle(reservation, listOfRentsByVehicleId)) {
				reservationService.create(reservation);
				response.sendRedirect("/rentmanager/rents");
			} else if (!ReservationValidator.startDateIsValid(reservation) || !ReservationValidator.endDateIsValid(reservation)) {
				throw new ReservationDateException(reservation.getDateStart().plus(8, ChronoUnit.DAYS));
			} else if (!ReservationValidator.rentalIsPossible(reservation, listOfRentsByClientId)
					|| !ReservationValidator.rentalIsPossible(reservation, listOfRentsByVehicleId)) {
				response.sendError(400, "Le véhicule sélectionné est déjà réservé entre le " + reservation.getDateStart() + " et le " + reservation.getDateEnd() + ".");
				throw new ReservationPossibleException(reservation.getDateStart(), reservation.getDateEnd());
			} else if (ReservationValidator.maxRentalTimeOfVehicleByOneUser(reservation, listOfRentsByClientId)) {
				response.sendError(400, "Le client " + reservation.getClient() + " ne peut réserver le véhicule " + reservation.getVehicle() + " que pour 7 jours maximum seulement");
				throw new ReservationTimeByOneUserException(reservation.getClient(), reservation.getVehicle());
			} else {
				response.sendError(400, "Le véhicule " + reservation.getVehicle() + " ne peut pas être réservé plus de 30 jours d'affilés.");
				throw new ReservationTimeForOneVehicleException(reservation.getVehicle());
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (ReservationDateException e) {
			e.printStackTrace();
		} catch (ReservationPossibleException e) {
			e.printStackTrace();
		} catch (ReservationTimeByOneUserException e) {
			e.printStackTrace();
		} catch (ReservationTimeForOneVehicleException e) {
			e.printStackTrace();
		}
	}

}
