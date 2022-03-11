package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

@WebServlet(urlPatterns = "/home")

public class HomeServlet extends HttpServlet {                         
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nbClients = 0;
		int nbVehicles = 0;
		int nbResa = 0;
		try {
			nbClients = clientService.count();
			nbVehicles = vehicleService.count();
			nbResa = reservationService.count();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("nbClients", nbClients);
		request.setAttribute("nbVehicles", nbVehicles);
		request.setAttribute("nbResa", nbResa);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp");
		dispatcher.forward(request, response);
	}
                        
}