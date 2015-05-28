package com.nearsoft.flights.interfaces;

import com.nearsoft.flights.vo.Airports;
import com.nearsoft.flights.vo.Flights;
import com.nearsoft.flights.vo.TripInformation;

public interface TravelAPIClient {	
	
	Airports getAllActiveAirports();
	
	Flights getDepartingFlightsByRouteAndDate(TripInformation origin);
}
