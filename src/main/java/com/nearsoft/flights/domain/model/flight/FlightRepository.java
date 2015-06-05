package com.nearsoft.flights.domain.model.flight;

import java.util.Set;

public interface FlightRepository {

	Set<Flight> findFlightsByDeparture(TripInformationRequest request);
	void add(Set<Flight> flights);
	
}
