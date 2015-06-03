package com.nearsoft.flights.domain.model.flight;

import java.util.Set;

import com.nearsoft.flights.domain.model.exception.RepositoryException;

public interface FlightRepository {

	Set<Flight> findFlightsByDeparture(TripInformationRequest request) throws RepositoryException;
	
}
