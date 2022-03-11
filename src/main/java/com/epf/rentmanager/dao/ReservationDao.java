package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

@Repository
@Scope("singleton")
public class ReservationDao {

	private ReservationDao() {
	}


	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin = ? WHERE id = ?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_CLIENT_ID_BY_VEHICLE_QUERY = "SELECT DISTINCT client_id FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATION_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) FROM Reservation;";

	public int create(Reservation reservation) throws DaoException {
		int numberCreated = 0;
		try {
			Connection conn = ConnectionManager.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(CREATE_RESERVATION_QUERY);

			pstmt.setInt(1, reservation.getClientId());
			pstmt.setInt(2, reservation.getVehicleId());
			pstmt.setDate(3, Date.valueOf(reservation.getDateStart()));
			pstmt.setDate(4, Date.valueOf(reservation.getDateEnd()));

			numberCreated = pstmt.executeUpdate();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberCreated;

	}
	
	public int update(Reservation reservation) throws DaoException {
		int numberUpdated = 0;
		try {
			Connection conn = ConnectionManager.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(UPDATE_RESERVATION_QUERY);

			pstmt.setInt(1, reservation.getClientId());
			pstmt.setInt(2, reservation.getVehicleId());
			pstmt.setDate(3, Date.valueOf(reservation.getDateStart()));
			pstmt.setDate(4, Date.valueOf(reservation.getDateEnd()));
			pstmt.setInt(5, reservation.getId());

			numberUpdated = pstmt.executeUpdate();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberUpdated;
	}

	public int delete(Reservation reservation) throws DaoException {
		int numberDeleted = 0;
		try {
			Connection conn = ConnectionManager.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(DELETE_RESERVATION_QUERY);
			pstmt.setInt(1, reservation.getId());

			numberDeleted = pstmt.executeUpdate();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberDeleted;

	}
	
	public Optional<Reservation> findResaById(int reservationId) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATION_QUERY);

			pstmt.setInt(1, reservationId);

			ResultSet rs = pstmt.executeQuery();

			rs.next();

			int clientId = rs.getInt("client_id");
			int vehicleId = rs.getInt("vehicle_id");
			LocalDate dateStart = rs.getDate("debut").toLocalDate();
			LocalDate dateEnd = rs.getDate("fin").toLocalDate();

			Reservation reservation = new Reservation(reservationId, clientId, vehicleId, dateStart, dateEnd);
			conn.close();
			return Optional.of(reservation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public List<Reservation> findResaByClientId(int clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);

			pstmt.setInt(1, clientId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int reservationId = rs.getInt("id");
				int vehicleId = rs.getInt("vehicle_id");
				LocalDate dateStart = rs.getDate("debut").toLocalDate();
				LocalDate dateEnd = rs.getDate("fin").toLocalDate();

				Reservation reservation = new Reservation(reservationId, clientId, vehicleId, dateStart, dateEnd);
				reservations.add(reservation);
			}
			conn.close();
			return reservations;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();

	}

	public List<Reservation> findResaByVehicleId(int vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);

			pstmt.setInt(1, vehicleId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int reservationId = rs.getInt("id");
				int ClientId = rs.getInt("client_id");
				LocalDate dateStart = rs.getDate("debut").toLocalDate();
				LocalDate dateEnd = rs.getDate("fin").toLocalDate();

				Reservation reservation = new Reservation(reservationId, ClientId, vehicleId, dateStart, dateEnd);
				reservations.add(reservation);
			}
			conn.close();
			return reservations;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();

	}
	
	public List<Integer> findClientIdByVehicleId(int vehicleId) throws DaoException {
		List<Integer> clientIds = new ArrayList<Integer>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENT_ID_BY_VEHICLE_QUERY);

			pstmt.setInt(1, vehicleId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int clientId = rs.getInt("client_id");
				clientIds.add(clientId);
			}
			conn.close();
			return clientIds;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_QUERY);

			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int reservationId = rs.getInt("id");
				int clientId = rs.getInt("client_id");
				int vehicleId = rs.getInt("vehicle_id");
				LocalDate dateStart = rs.getDate("debut").toLocalDate();
				LocalDate dateEnd = rs.getDate("fin").toLocalDate();

				Reservation reservation = new Reservation(reservationId, clientId, vehicleId, dateStart, dateEnd);

				reservations.add(reservation);
			}
			conn.close();
			return reservations;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	public int count() throws DaoException {
		int nbResa = 0;
		try {
			Connection cm = ConnectionManager.getConnection();
			PreparedStatement pstmt = cm.prepareStatement(COUNT_RESERVATIONS_QUERY);
			ResultSet rs = pstmt.executeQuery();

			rs.last();

			nbResa = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nbResa;
	}
}
