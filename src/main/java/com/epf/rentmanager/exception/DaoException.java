package com.epf.rentmanager.exception;

public class DaoException extends Exception {
	private static final long serialVersionUID = 1L;

	public DaoException() {
		super("Erreur au niveau du DAO");
	}

}
