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
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;


@Repository
@Scope("singleton")
public class ClientDao {
	
	
	public ClientDao() {
	}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(id, nom, prenom, email, naissance) VALUES(?, ?, ?, ?, ?);";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id = ?;";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) FROM Client;";

	public int create(Client client) throws DaoException {
		int row = 0;
		try {
			Connection conn = ConnectionManager.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(CREATE_CLIENT_QUERY);
			
			pstmt.setInt(1, this.count() + 1);
			pstmt.setString(2, client.getLastname().toUpperCase());
			pstmt.setString(3, client.getFirstname());
			pstmt.setString(4, client.getEmail());
			pstmt.setDate(5, Date.valueOf(client.getBirthdate()));
			
			row = pstmt.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DaoException e) {
			throw new DaoException();
		}
		return row;
	}
	
	public int update(Client client) throws DaoException {
		int numberUpdated = 0;
		try {
			Connection conn = ConnectionManager.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(UPDATE_CLIENT_QUERY);
			
			pstmt.setString(1, client.getLastname().toUpperCase());
			pstmt.setString(2, client.getFirstname());
			pstmt.setString(3, client.getEmail());
			pstmt.setDate(4, Date.valueOf(client.getBirthdate()));
			pstmt.setInt(5, client.getId());
			

			numberUpdated = pstmt.executeUpdate();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberUpdated;
		
	}

	public int delete(Client client) throws DaoException {
		int numberDeleted = 0;
		try {
			Connection conn = ConnectionManager.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(DELETE_CLIENT_QUERY);
			pstmt.setInt(1, client.getId());

			numberDeleted = pstmt.executeUpdate();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberDeleted;
	}

	public Optional<Client> findById(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENT_QUERY);

			pstmt.setInt(1, id);


			ResultSet rs = pstmt.executeQuery();

			rs.next();

			int clientId = rs.getInt("id");
			String clientLastName = rs.getString("nom");
			String clientFirstName = rs.getString("prenom");
			String clientEmail = rs.getString("email");
			LocalDate clientBirthDate = rs.getDate("naissance").toLocalDate();
			

			Client client = new Client(clientId, clientLastName, clientFirstName, clientEmail, clientBirthDate);
			conn.close();

			return Optional.of(client);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<Client>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENTS_QUERY);

			ResultSet rs = pstmt.executeQuery();

			
			while (rs.next()) {
				int clientId = rs.getInt("id");
				String clientLastName = rs.getString("nom");
				String clientFirstName = rs.getString("prenom");
				String clientEmail = rs.getString("email");
				LocalDate clientBirthDate = rs.getDate("naissance").toLocalDate();

				Client client = new Client(clientId, clientLastName, clientFirstName, clientEmail, clientBirthDate);

				clients.add(client);

			}
			conn.close();
			return clients;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}
	
	public int count() throws DaoException {
		int nbClients = 0;
		try {
			Connection cm = ConnectionManager.getConnection();
			PreparedStatement pstmt = cm.prepareStatement(COUNT_CLIENTS_QUERY);
			ResultSet rs = pstmt.executeQuery();
			
			rs.last();
			
			nbClients = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nbClients;
	}
}