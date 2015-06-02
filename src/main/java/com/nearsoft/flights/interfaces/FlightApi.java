package com.nearsoft.flights.interfaces;

import java.util.Set;

import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.persistence.dto.TripInformationRequestDto;

public interface FlightApi {
	Set<Flight> getDepartingFlightsByTripInformation(TripInformationRequestDto tripInformationRequest);
}
