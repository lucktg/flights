package com.nearsoft.flights.domain.model.flight;

import java.util.Set;

public interface FlightRepository {

	Set<Flight> findDepartingFlightsByTripInformationRequest(TripInformationRequest request);
	void add(Set<Flight> flights);
	void removeAll();
	
}
