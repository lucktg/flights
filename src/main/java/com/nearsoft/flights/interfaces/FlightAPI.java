package com.nearsoft.flights.interfaces;

import com.nearsoft.flights.vo.Flights;
import com.nearsoft.flights.vo.TripInformation;

public interface FlightAPI {
	Flights getDepartingFlightsByRouteAndDate(TripInformation origin);
}
