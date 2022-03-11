package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
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

@WebServlet(urlPatterns = "/rents")
public class ReservationListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Reservation> reservationList = reservationService.findAll();
			for (Reservation rent : reservationList) {

				Client client = clientService.findById(rent.getClientId());
				String clientFullName = client.getFirstname() + " " + client.getLastname();
				rent.setClient(clientFullName);

				Vehicle vehicle = vehicleService.findById(rent.getVehicleId());
				String vehiclename = vehicle.getConstructor() + " " + vehicle.getModel();
				rent.setVehicle(vehiclename);
			}
			
			request.setAttribute("rents", reservationList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp");
			dispatcher.forward(request, response);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int reservationId = Integer.parseInt(request.getParameter("delete"));
		Reservation reservation = new Reservation(reservationId, 0, 0, null, null);
		
		try {
			reservationService.delete(reservation);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/rentmanager/rents");
	}

}
