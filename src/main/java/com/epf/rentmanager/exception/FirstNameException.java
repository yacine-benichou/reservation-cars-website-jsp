package com.epf.rentmanager.exception;

public class FirstNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	private int number;
	
	public FirstNameException(String str, int id) {
		this.message = str;
		this.number = id;
	}

	@Override
	public String toString() {
		return "Le prénom " + this.message + " du client avec l'id " +  this.number + " doit être composé d'au moins 3 caractères.";
	}

}
