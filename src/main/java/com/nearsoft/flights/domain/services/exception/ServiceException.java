package com.nearsoft.flights.domain.services.exception;

import com.nearsoft.flights.domain.model.exception.RepositoryException;

public class ServiceException extends Exception {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1986204143244676581L;

	public ServiceException(String string, RepositoryException e) {
		super(string, e);
	}

}
