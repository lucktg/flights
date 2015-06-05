package com.nearsoft.flights.domain.model.airport;

import java.util.Set;

public interface AirportRepository {
	Set<Airport> findAllActiveAirports();
	Airport findByAirportCode(String airportCode);
	void add(Set<Airport> airports);
	void add(Airport airport);
	void removeAll();
}
