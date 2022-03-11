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
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;


@WebServlet(urlPatterns = "/cars/details")
public class VehicleDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	ClientService clientService;
	
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
		List<Client> users = new ArrayList<Client>();
		int vehicleId = Integer.parseInt(request.getParameter("id"));

		try {
			List<Reservation> rents = reservationService.findResaByVehicleId(vehicleId);
			for (Reservation rent : rents) {
				Client client = clientService.findById(rent.getClientId());
				String clientName = client.getLastname() + " " + client.getFirstname();
				rent.setClient(clientName);
			}

			List<Integer> usersIds = reservationService.findClientIdByVehicleId(vehicleId);
			for (int userId : usersIds) {
				Client client = clientService.findById(userId);
				users.add(client);
			}

			request.setAttribute("rents", rents);
			request.setAttribute("users", users);
			request.setAttribute("nbResa", rents.size());
			request.setAttribute("nbUser", users.size());

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp");
			dispatcher.forward(request, response);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
