package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
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

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;



@WebServlet(urlPatterns = "/users/details")
public class ClientDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	ReservationService reservationService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Reservation> rents = new ArrayList<>();
		List<Vehicle> cars = new ArrayList<>();
		int nbResa = 0;
		int nbCar = 0;
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			Client client = clientService.findById(id);
			rents = reservationService.findResaByClientId(id);
			for (Reservation rent : rents) {
				Vehicle vehicle = vehicleService.findById(rent.getVehicleId());
				String vehiclename = vehicle.getConstructor() + " " + vehicle.getModel();
				rent.setVehicle(vehiclename);
			}
			
			cars = vehicleService.findVehicleByClientId(id);
			nbResa = rents.size();
			nbCar = cars.size();

			request.setAttribute("userName", client.getLastname() + " " + client.getFirstname());
			request.setAttribute("userEmail", client.getEmail());
			request.setAttribute("rents", rents);
			request.setAttribute("cars", cars);
			request.setAttribute("nbResa", nbResa);
			request.setAttribute("nbCar", nbCar);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp");
			dispatcher.forward(request, response);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
