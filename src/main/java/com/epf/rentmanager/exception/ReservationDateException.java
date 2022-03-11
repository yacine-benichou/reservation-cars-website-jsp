package com.epf.rentmanager.exception;

import java.time.LocalDate;

public class ReservationDateException extends Exception {
	private static final long serialVersionUID = 1L;
	private LocalDate limitEndDate;
	
	public ReservationDateException(LocalDate limitEndDate) {
		this.limitEndDate = limitEndDate;
	}

	@Override
	public String toString() {
		return "Attention ! La date de début de réservation créée/modifiée doit être avant aujourd'hui et "
				+ "la date de fin de réservation ne peut pas dépasser le " + this.limitEndDate + ".";
	}
	
	

}
