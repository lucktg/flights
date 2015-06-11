package com.nearsoft.flights.domain.model.exception;

import java.sql.SQLException;

public class RepositoryException extends RuntimeException {

	public RepositoryException(String string, SQLException ex) {
		super(string, ex);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8094544338330477847L;

}
