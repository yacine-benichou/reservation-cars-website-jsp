package com.epf.rentmanager.validator;

import com.epf.rentmanager.model.Vehicle;

public class VehicleValidator {
	
	public static boolean isNbSeatsValid(Vehicle vehicle) {
		if (vehicle.getNbPlace() > 1 && vehicle.getNbPlace() < 10) {
			return true;
		}
		return false;
	}
}
