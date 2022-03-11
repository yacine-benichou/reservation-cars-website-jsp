package com.epf.rentmanager.exception;

public class ReservationTimeByOneUserException extends Exception {
	private static final long serialVersionUID = 1L;
	private String client;
	private String vehicle;
	
	public ReservationTimeByOneUserException(String client, String vehicle) {
		this.client = client;
		this.vehicle = vehicle;
	}

	@Override
	public String toString() {
		return "Le client " + this.client + " ne peut réserver le véhicule " + this.vehicle + " que pour 7 jours maximum uniquement.";
	}
	
	

}
