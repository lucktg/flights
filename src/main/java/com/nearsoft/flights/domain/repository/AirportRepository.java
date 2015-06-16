package com.nearsoft.flights.domain.repository;

import com.nearsoft.flights.domain.model.Airport;

public interface AirportRepository extends Repository<Airport> {

	Airport getByAirportCode(String airportCode);
	
}
