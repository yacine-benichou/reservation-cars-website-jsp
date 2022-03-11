package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

@Repository
@Scope("singleton")
public class VehicleDao {


	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(id, constructeur, modele, nb_places) VALUES(?, ?, ?, ?);";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id = ?"; 
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String FIND_VEHICLE_BY_CLIENT_QUERY = "SELECT DISTINCT V.id, constructeur, modele, nb_places FROM Reservation AS R INNER JOIN Vehicle AS V ON R.vehicle_id = V.id WHERE R.client_id=?;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle;";

	public int create(Vehicle vehicle) throws DaoException {
		int numberCreated = 0;
		try {
			Connection conn = ConnectionManager.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(CREATE_VEHICLE_QUERY);
			
			pstmt.setInt(1, this.count() + 1);
			pstmt.setString(2, vehicle.getConstructor());
			pstmt.setString(3, vehicle.getModel());
			pstmt.setInt(4, vehicle.getNbPlace());

			numberCreated = pstmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberCreated;

	}
	
	public int update(Vehicle vehicle) throws DaoException {
		int numberUpdated = 0;
		try {
			Connection conn = ConnectionManager.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(UPDATE_VEHICLE_QUERY);
			
			pstmt.setString(1, vehicle.getConstructor());
			pstmt.setString(2, vehicle.getModel());
			pstmt.setInt(3, vehicle.getNbPlace());
			pstmt.setInt(4, vehicle.getId());

			numberUpdated = pstmt.executeUpdate();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberUpdated;
		
	}

	public int delete(Vehicle vehicle) throws DaoException {
		int numberDeleted = 0;
		try {
			Connection conn = ConnectionManager.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(DELETE_VEHICLE_QUERY);
			pstmt.setInt(1, vehicle.getId());

			pstmt.executeUpdate();
			numberDeleted++;
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberDeleted;

	}

	public Optional<Vehicle> findById(int id) throws DaoException {
		try {
			Connection cm = ConnectionManager.getConnection();
			PreparedStatement pstmt = cm.prepareStatement(FIND_VEHICLE_QUERY);

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			int vehicleId = rs.getInt("id");
			String vehicleConstructor = rs.getString("constructeur");
			String vehicleModel = rs.getString("modele");
			int vehicleNbPlaces = rs.getInt("nb_places");
			
			Vehicle vehicle = new Vehicle(vehicleId, vehicleConstructor, vehicleModel, vehicleNbPlaces);
			cm.close();

			return Optional.of(vehicle);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicleList = new ArrayList<>();

		try {
			Connection cm = ConnectionManager.getConnection();
			PreparedStatement pstmt = cm.prepareStatement(FIND_VEHICLES_QUERY);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int vehicleId = rs.getInt("id");
				String vehicleConstructor = rs.getString("constructeur");
				String vehicleModel = rs.getString("modele");
				int vehicleNbPlaces = rs.getInt("nb_places");

				Vehicle vehicle = new Vehicle(vehicleId, vehicleConstructor, vehicleModel, vehicleNbPlaces);

				vehicleList.add(vehicle);
			}
			cm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return vehicleList;
	}
	
	public int count() throws DaoException {
		int nbVehicles = 0;
		try {
			Connection cm = ConnectionManager.getConnection();
			PreparedStatement pstmt = cm.prepareStatement(COUNT_VEHICLES_QUERY);
			ResultSet rs = pstmt.executeQuery();
			
			rs.last();
			
			nbVehicles = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nbVehicles;
	}
	
	public List<Vehicle> findVehicleByClientId(int clientId) throws DaoException {
		List<Vehicle> vehicules = new ArrayList<Vehicle>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLE_BY_CLIENT_QUERY);
			pstmt.setInt(1, clientId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int vehicleId = rs.getInt("id");
				String constructeur = rs.getString("modele");
				String modele = rs.getString("constructeur");
				int nb_places = rs.getInt("nb_places");

				Vehicle vehicule = new Vehicle(vehicleId, constructeur, modele, nb_places);

				vehicules.add(vehicule);
			}
			conn.close();
			return vehicules;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}
	
}
