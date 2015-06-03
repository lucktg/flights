package com.nearsoft.flights.domain.model.airport;

import java.util.Set;

import com.nearsoft.flights.domain.model.exception.RepositoryException;

public interface AirportRepository {
	
	Set<Airport> findAllActiveAirports() throws RepositoryException;
	Airport findByAirportCode(String airportCode) throws RepositoryException;
}
