package com.nearsoft.flights.persistence.dao;

import java.util.Set;

import com.nearsoft.flights.domain.model.flight.TripInformationRequest;
import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;
import com.nearsoft.flights.persistence.dto.FlightDto;
@Deprecated
public interface FlightDao {

	void insertFlights(Set<FlightDto> flights) throws PersistenceException;
	Set<FlightDto> findDepartingFlightsByTripInformationRequest(TripInformationRequest tripInformationRequest) throws PersistenceException;
	void deleteAll() throws PersistenceException;
}
