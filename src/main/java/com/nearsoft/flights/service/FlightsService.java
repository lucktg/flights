package com.nearsoft.flights.service;

import com.nearsoft.flights.vo.RoundTrip;
import com.nearsoft.flights.vo.TripInformationRequest;

public interface FlightsService {
	RoundTrip getRoundTripFlights(TripInformationRequest origin, TripInformationRequest destiny);
}
