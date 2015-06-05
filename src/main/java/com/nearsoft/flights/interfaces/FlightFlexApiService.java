package com.nearsoft.flights.interfaces;

import java.util.Set;

import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;

public interface FlightFlexApiService {
	Set<Flight> getDepartingFlightsByTripInformation(TripInformationRequest tripInformationRequest);
}
