package com.nearsoft.flights.interfaces;

import java.util.Set;

import com.nearsoft.flights.domain.model.Airport;

public interface AirportApiService {
	Set<Airport> getAllActiveAirports();
	Airport getAirportByCode(String airportCode);
}
