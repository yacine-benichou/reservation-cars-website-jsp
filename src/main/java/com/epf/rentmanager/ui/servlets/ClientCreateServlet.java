package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

@WebServlet(urlPatterns = "/users/create")
public class ClientCreateServlet extends HttpServlet {
	
	@Autowired
	private ClientService clientService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	private static final long serialVersionUID = 1L;
	private static int nbClients = 0;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			nbClients = clientService.count();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String Nom = request.getParameter("last_name");
		String Prenom = request.getParameter("first_name");
		String Email = request.getParameter("email");
		String birthDateString = request.getParameter("birthDate");

		LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
		int clientId = nbClients + 1;
		
		try {
			List<Client> clientList = clientService.findAll();
			Client client = new Client(clientId, Nom, Prenom, Email, birthDate);
			if (ClientValidator.isLegal(client)) {
				if (ClientValidator.isEmailNotTaken(client, clientList)) {
					clientService.create(client);
					response.sendRedirect("/rentmanager/users");
				} else {
					response.sendError(400, "Le mail " + client.getEmail() + " est déjà pris par un autre client.");
					throw new EmailException(client.getEmail());
				}	
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
