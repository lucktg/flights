package com.nearsoft.flights.interfaces;

import java.util.Set;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.flight.Flight;

public interface AirportAPI {
	Set<Airport> getAllActiveAirports();
	Airport getAirportByCode(String airportCode);
}
