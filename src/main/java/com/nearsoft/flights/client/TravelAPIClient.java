package com.nearsoft.flights.client;

import java.util.Date;

import com.nearsoft.flights.vo.Airports;
import com.nearsoft.flights.vo.Flights;
import com.nearsoft.flights.vo.TripInformation;

public interface TravelAPIClient {	
	
	Airports getAllActiveAirports();
	
	Flights getDepartingFlightsByRouteAndDate(TripInformation origin);
}
