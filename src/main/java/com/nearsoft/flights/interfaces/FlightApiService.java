package com.nearsoft.flights.interfaces;

import java.util.Set;

import com.nearsoft.flights.domain.model.Flight;
import com.nearsoft.flights.domain.model.TripInformationRequest;

public interface FlightApiService {
	Set<Flight> getDepartingFlightsByTripInformation(TripInformationRequest tripInformationRequest);
}
