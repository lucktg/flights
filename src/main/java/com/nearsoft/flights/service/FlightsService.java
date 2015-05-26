package com.nearsoft.flights.service;

import com.nearsoft.flights.vo.RoundTrip;
import com.nearsoft.flights.vo.TripInformation;

public interface FlightsService {
	RoundTrip getRoundTripFlights(TripInformation origin, TripInformation destiny);
}
