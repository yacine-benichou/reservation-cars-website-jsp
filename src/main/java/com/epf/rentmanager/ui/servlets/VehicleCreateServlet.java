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
import com.epf.rentmanager.exception.VehiclePlaceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

@WebServlet(urlPatterns = "/cars/create")
public class VehicleCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int nbVehicles = 0;
	
	@Autowired
	private VehicleService vehicleService;
	
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			nbVehicles = vehicleService.count();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
		dispatcher.forward(request, response);
	}
                        
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String marque = request.getParameter("manufacturer");
		String modele = request.getParameter("modele");
		String seats = request.getParameter("seats");
		int nbPlace = Integer.parseInt(seats);
		Vehicle vehicle = new Vehicle(nbVehicles + 1, marque, modele, nbPlace);
		   
		try {
			vehicleService.create(vehicle);
			response.sendRedirect("/rentmanager/cars");
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (VehiclePlaceException e) {
			e.printStackTrace();
		}
	}
}
