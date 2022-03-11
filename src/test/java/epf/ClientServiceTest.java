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
import com.epf.rentmanager.exception.AgeException;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.EmailException;
import com.epf.rentmanager.exception.FirstNameException;
import com.epf.rentmanager.exception.LastNameException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = {TestConfiguration.class})
public class ClientServiceTest {
	
	@Autowired
	ClientService clientService;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	

	@Test
	public void shouldCreateClient() throws ServiceException, EmailException, AgeException, FirstNameException, LastNameException, DaoException {
		LocalDate birthDate = LocalDate.parse("14/03/1990", formatter);
		Client client = new Client(5, "EINSTEIN", "Albert", "albert.einstein@email.com", birthDate);
		assertTrue("Le client n'a pas été crée !", clientService.create(client) > 0);
	}
	
	@Test
	public void shouldFindClientById() throws ServiceException {
		LocalDate birthDate = LocalDate.parse("22/01/1988", formatter);
		Client client = new Client(2, "MORIN", "Sabrina", "sabrina.morin@email.com", birthDate);
		Client clientFound = clientService.findById(client.getId());
		System.out.println(client);
		System.out.println(clientFound);
		boolean areClientsEquals = clientFound.getLastname().equals(client.getLastname()) && clientFound.getFirstname().equals(client.getFirstname())
							&& clientFound.getEmail().equals(client.getEmail()) && clientFound.getBirthdate().equals(client.getBirthdate());
		assertTrue("Le client n'a pas été trouvé !", areClientsEquals);
	}
	
	@Test
	public void isListClientsNotNull() throws ServiceException {
		assertNotEquals(null, clientService.findAll());
	}
	
	@Test
	public void isListClientsNotEmpty() throws ServiceException {
		assertTrue("La liste de clients est vide !", !clientService.findAll().isEmpty());
	}
	
	@Test
	public void shouldUpdateClient() throws ServiceException, EmailException, AgeException, FirstNameException, LastNameException, DaoException {
		LocalDate birthDate = LocalDate.parse("14/03/1990", formatter);
		Client client = new Client(5, "EINSTEIN", "Albert", "albert.einstein@email.com", birthDate);
		assertTrue("Le client n'a pas été modifié !", clientService.update(client) > 0);
	}
	
	@Test 
	public void shouldDeleteClient() throws ServiceException {
		LocalDate birthDate = LocalDate.parse("14/03/1990", formatter);
		Client client = new Client(5, "EINSTEIN", "Albert", "albert.einstein@email.com", birthDate);
		assertTrue("Le client " + client.getFirstname() + " " + client.getLastname() + " n'a pas été supprimé !", clientService.delete(client) > 0);
	}
	
	@Test
	public void shouldClientCountGreaterThan0() throws ServiceException {
		assertTrue("Le nombre de clients est égal à 0 !", clientService.count() > 0);
	}

}
