package com.nearsoft.flights.domain.repository;

import java.util.List;

import com.nearsoft.flights.domain.model.Flight;
import com.nearsoft.flights.domain.model.TripInformationRequest;

public interface FlightRepository extends Repository<Flight> {
	
	List<Flight> getBytTripInformation(TripInformationRequest tripInformation);
}
