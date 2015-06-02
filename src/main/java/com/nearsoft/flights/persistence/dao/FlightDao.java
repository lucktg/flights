package com.nearsoft.flights.persistence.dao;

import java.util.Set;

import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;
import com.nearsoft.flights.persistence.dto.FlightDto;
import com.nearsoft.flights.persistence.dto.TripInformationRequestDto;

public interface FlightDao {

	void insertFlights(Set<FlightDto> flights) throws PersistenceException;
	Set<FlightDto> findDepartingFlightsByTripInformationRequest(TripInformationRequestDto tripInformationRequest) throws PersistenceException;
	void deleteAll() throws PersistenceException;
}
