package com.nearsoft.flights.domain.services;

import java.util.Set;

import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.RoundTrip;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;
import com.nearsoft.flights.domain.services.exception.ServiceException;

public interface FlightsService {
	RoundTrip getRoundTripFlights(TripInformationRequest origin, TripInformationRequest destiny) throws ServiceException;
	Set<Flight> getOneWayFlights(TripInformationRequest origin) throws ServiceException;
}
