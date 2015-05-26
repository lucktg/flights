package com.nearsoft.flights.dao;

import java.util.Date;
import java.util.Set;

import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.TripInformation;

public interface FlightDAO {

	void updateFlights(Set<Flight> flights);

	Set<Flight> findDepartingFlightsByRouteNDate(TripInformation tripInformation);
}
