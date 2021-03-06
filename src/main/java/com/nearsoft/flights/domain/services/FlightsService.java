package com.nearsoft.flights.domain.services;

import java.util.Set;

import com.nearsoft.flights.domain.model.Flight;
import com.nearsoft.flights.domain.model.RoundTrip;
import com.nearsoft.flights.domain.model.TripInformationRequest;

public interface FlightsService {
	RoundTrip getRoundTripFlights(TripInformationRequest origin, TripInformationRequest destiny);
	Set<Flight> getOneWayFlights(TripInformationRequest origin);
}
