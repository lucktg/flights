package com.nearsoft.flights.data.getters;
import java.util.Set;

import com.nearsoft.flights.vo.Airport;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.TripInformation;

public interface TravelDataGetter {
	
	Set<Airport> getAllActiveAirports();
	Set<Flight> getDepartingFlightsByRouteNDate(TripInformation tripInformation);
}
