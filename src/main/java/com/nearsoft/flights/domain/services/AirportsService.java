package com.nearsoft.flights.domain.services;

import java.util.Set;

import com.nearsoft.flights.domain.model.Airport;

public interface AirportsService {
	Set<Airport> getActiveAirports();
	Airport getAirportByCode(String airportCode);
}
