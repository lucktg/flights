package com.nearsoft.flights.interfaces;

import java.util.Set;

import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.vo.TripInformationRequest;

public interface FlightApi {
	Set<Flight> getDepartingFlightsByTripInformation(TripInformationRequest tripInformationRequest);
}
