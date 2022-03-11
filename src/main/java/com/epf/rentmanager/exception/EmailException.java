package com.epf.rentmanager.exception;

public class EmailException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public EmailException(String str) {
		this.message = str;
	}

	@Override
	public String toString() {
		return "Le mail " + this.message + " est déjà pris par un autre client.";
	}
	
	
}
