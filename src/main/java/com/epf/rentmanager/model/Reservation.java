package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {

	private int id;
	private int clientId;
	private String client;
	private int vehicleId;
	private String vehicle;
	private LocalDate dateStart;
	private LocalDate dateEnd;

	public Reservation(int id, int clientId, int vehicleId, LocalDate dateStart, LocalDate dateEnd) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.vehicleId = vehicleId;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	public Reservation(int id, String client, String vehicle, LocalDate dateStart, LocalDate dateEnd) {
		super();
		this.id = id;
		this.client = client;
		this.vehicle = vehicle;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
	
	

	public Reservation(int id, int clientId, String vehicle, LocalDate dateStart, LocalDate dateEnd) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.vehicle = vehicle;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	public Reservation() {
		
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public int getId() {
		return id;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public LocalDate getDateStart() {
		return dateStart;
	}

	public void setDateStart(LocalDate dateStart) {
		this.dateStart = dateStart;
	}

	public LocalDate getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(LocalDate dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", clientId=" + clientId + ", client=" + client + ", vehicleId=" + vehicleId
				+ ", vehicle=" + vehicle + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + "]";
	}

}
