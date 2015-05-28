package com.nearsoft.flights.domain.model.airport;

import java.util.List;

public interface AirportRepository {
	
	List<Airport> findAllActiveAirports();
}
