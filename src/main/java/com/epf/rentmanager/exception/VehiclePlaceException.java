package com.epf.rentmanager.exception;

public class VehiclePlaceException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private int place;
	
	public VehiclePlaceException(int place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "Le véhicule que vous essayez de créer ou de modifier doit avoir entre 2 et 9 places. Cependant, vous avez écrit " + this.place + " places.";
	}
}
