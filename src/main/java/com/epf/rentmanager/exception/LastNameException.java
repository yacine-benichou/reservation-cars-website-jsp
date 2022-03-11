package com.epf.rentmanager.exception;

public class LastNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private int number;

	public LastNameException(String str, int id) {
		this.message = str;
		this.number = id;
	}

	@Override
	public String toString() {
		return "Le nom de famille " + this.message + " du client avec l'id " +  this.number + " doit être composé d'au moins 3 caractères.";
	}

}
