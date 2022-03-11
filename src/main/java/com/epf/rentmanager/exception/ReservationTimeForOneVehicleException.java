package com.epf.rentmanager.exception;

public class ReservationTimeForOneVehicleException extends Exception {
	private static final long serialVersionUID = 1L;
	private String vehicle;
	
	public ReservationTimeForOneVehicleException(String vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public String toString() {
		return "Le véhicule " + this.vehicle + " ne peut pas être réservé plus de 30 jours d'affilés.";
	}
	
	

}
