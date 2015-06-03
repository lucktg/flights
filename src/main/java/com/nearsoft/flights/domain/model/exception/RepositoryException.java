package com.nearsoft.flights.domain.model.exception;


public class RepositoryException extends Exception {

	public RepositoryException(String string, Exception e) {
		super(string, e);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8077520438787515404L;

}
