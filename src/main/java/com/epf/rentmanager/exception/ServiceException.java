package com.epf.rentmanager.exception;

public class ServiceException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super("Exception au niveau du service : Un champ est vide lors de la création ou mise à jour d'un client, d'un véhicule ou d'une réservation");
	}

}
