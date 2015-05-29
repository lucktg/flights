package com.nearsoft.flights.interfaces;

import java.util.Set;

import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.vo.Flights;
import com.nearsoft.flights.vo.TripInformation;

public interface FlightAPI {
	Set<Flight> getDepartingFlightsByRouteAndDate(TripInformation origin);
}
