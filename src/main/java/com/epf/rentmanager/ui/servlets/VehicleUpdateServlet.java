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

@WebServlet(urlPatterns = "/cars/update")
public class VehicleUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
			Vehicle vehicleToUpdate = vehicleService.findById(Integer.parseInt(request.getParameter("id")));
			String vehicleManufacturer = vehicleToUpdate.getConstructor();
			String vehicleModel = vehicleToUpdate.getModel();
			int vehiclePlaces = vehicleToUpdate.getNbPlace();
			
			request.setAttribute("vehicleManufacturer", vehicleManufacturer);
			request.setAttribute("vehicleModel", vehicleModel);
			request.setAttribute("vehiclePlaces", vehiclePlaces);
			System.out.println(request.getAttribute(vehicleModel));
		} catch (NumberFormatException | ServiceException e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String constructor = request.getParameter("manufacturer");
		String model = request.getParameter("modele");
		int seats = Integer.parseInt(request.getParameter("seats"));
		
		Vehicle vehicle = new Vehicle(Integer.parseInt(request.getParameter("id")), constructor, model, seats);
		try {
			vehicleService.update(vehicle);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (VehiclePlaceException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/rentmanager/cars");
	}
	
}
