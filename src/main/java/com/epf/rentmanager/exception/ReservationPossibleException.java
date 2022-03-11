package com.epf.rentmanager.exception;

import java.time.LocalDate;

public class ReservationPossibleException extends Exception{
	private static final long serialVersionUID = 1L;
	private LocalDate dateStart;
	private LocalDate dateEnd;
	
	public ReservationPossibleException(LocalDate dateStart, LocalDate dateEnd) {
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	@Override
	public String toString() {
		return "Le véhicule sélectionné est déjà réservé entre le " + this.dateStart + " et le " + this.dateEnd + ".";
	}
	
	

}
