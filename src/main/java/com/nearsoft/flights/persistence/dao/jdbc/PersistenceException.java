package com.nearsoft.flights.persistence.dao.jdbc;

import java.sql.SQLException;

public class PersistenceException extends RuntimeException {

	public PersistenceException(String string, SQLException ex) {
		super(string, ex);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8094544338330477847L;

}
