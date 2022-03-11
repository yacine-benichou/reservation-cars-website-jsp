package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.exception.AgeException;
import com.epf.rentmanager.exception.EmailException;
import com.epf.rentmanager.exception.FirstNameException;
import com.epf.rentmanager.exception.LastNameException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.validator.ClientValidator;

@WebServlet(urlPatterns = "/users/update")
public class ClientUpdateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ClientService clientService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Client clientToUpdate = clientService.findById(Integer.parseInt(request.getParameter("id")));
			String clientFirstName = clientToUpdate.getFirstname();
			String clientLastName = clientToUpdate.getLastname();
			String clientEmail = clientToUpdate.getEmail();
			LocalDate clientBirthDate = clientToUpdate.getBirthdate();
			
			request.setAttribute("firstName", clientFirstName);
			request.setAttribute("lastName", clientLastName);
			request.setAttribute("email", clientEmail);
			request.setAttribute("birthDate", clientBirthDate);
		} catch (NumberFormatException | ServiceException e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/users/update.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String lastName = request.getParameter("last_name");
		String firstName = request.getParameter("first_name");
		String email = request.getParameter("email");
		String birthDateString = request.getParameter("birthDate");

		LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
			
			try {
				Client client = new Client(Integer.parseInt(request.getParameter("id")), lastName, firstName, email, birthDate);
				if (ClientValidator.isLegal(client)) {
					clientService.update(client);
					response.sendRedirect("/rentmanager/users");	
				} else {
					response.sendError(400, "Le client " + client.getLastname() + " " + client.getFirstname() + " est âgé de moins de 18 ans.");
					throw new AgeException(client.getLastname() + " " + client.getFirstname());
				}
			} catch (ServiceException e) {
			e.printStackTrace();
			} catch (EmailException e) {
				e.printStackTrace();
			} catch (AgeException e) {
			e.printStackTrace();
			} catch (FirstNameException e) {
				e.printStackTrace();
			} catch (LastNameException e) {
				e.printStackTrace();
			}
		
	}

}
