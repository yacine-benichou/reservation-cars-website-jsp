package com.epf.rentmanager.exception;

public class AgeException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	
	public AgeException(String str) {
		this.message = str;
	}

	@Override
	public String toString() {
		return "Age Exception occured : Le client " + this.message + " est âgé de moins de 18 ans.";
	}
	
}
