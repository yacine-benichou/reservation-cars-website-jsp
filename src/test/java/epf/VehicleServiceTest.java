package epf;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.epf.rentmanager.configuration.TestConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.VehiclePlaceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = {TestConfiguration.class})
public class VehicleServiceTest {
	
	@Autowired
	 VehicleService vehicleService;
	
	@Autowired
	ClientService clientService;
	
	
	@Test
	public void shouldCreateVehicle() throws ServiceException, DaoException, VehiclePlaceException {
		Vehicle vehicle = new Vehicle(5, "Tesla", "Y", 4);
		assertTrue("Le vehicule n'a pas été crée !", vehicleService.create(vehicle) > 0);
	}
	
	@Test
	public void shouldFindVehicleById() throws ServiceException {
		Vehicle vehicle = new Vehicle(4, "Nissan", "Qashqai", 4);
		int vehicleId = vehicle.getId();
		Vehicle vehicleFound = vehicleService.findById(vehicleId);
		boolean areVehiclesEquals = vehicleFound.getConstructor().equals(vehicle.getConstructor()) && vehicleFound.getModel().equals(vehicle.getModel()) 
				&& vehicleFound.getNbPlace() == vehicle.getNbPlace();
		assertTrue("Le vehicule n'a pas été trouvé !", areVehiclesEquals);
	}
	
	@Test
	public void isListVehiclesNotNull() throws ServiceException {
		assertNotEquals(null, vehicleService.findAll());
	}
	
	@Test
	public void isListVehiclesNotEmpty() throws ServiceException {
		assertTrue("La liste des véhicules est vide !", !vehicleService.findAll().isEmpty());
	}
	
	@Test
	public void shouldUpdateVehicle() throws ServiceException, DaoException, VehiclePlaceException {
		Vehicle vehicle = new Vehicle(5, "Tesla", "Y", 4);
		assertTrue("Le véhicule n'a pas été modifié !", vehicleService.update(vehicle) > 0);
	}
	
	@Test 
	public void shouldDeleteVehicle() throws ServiceException {
		Vehicle vehicle = new Vehicle(5, "Tesla", "Y", 4);
		assertTrue("Le véhicule " + vehicle.getConstructor() + " " + vehicle.getModel() + " n'a pas été supprimé !", vehicleService.delete(vehicle) > 0);
	}
	
	@Test
	public void shouldVehicleCountGreaterThan0() throws ServiceException {
		assertTrue("Le nombre de véhicules est égal à 0 !", vehicleService.count() > 0);
	}
	
	@Test
	public void shouldFindVehicleByClientId() throws ServiceException {
		String errorMessage = "La liste de véhicule réservés par " + clientService.findById(2).getLastname() + " " + clientService.findById(2).getFirstname() + " est vide ou nulle !";
		assertTrue(errorMessage, vehicleService.findVehicleByClientId(2) != null && !vehicleService.findVehicleByClientId(2).isEmpty());
	}
	
}
