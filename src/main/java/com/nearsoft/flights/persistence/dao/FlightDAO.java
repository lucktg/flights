package com.nearsoft.flights.persistence.dao;

import java.util.Set;

import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.TripInformationRequest;

public interface FlightDAO {

	void updateFlights(Set<Flight> flights);

	Set<Flight> findDepartingFlightsByRouteNDate(TripInformationRequest tripInformation);
}
