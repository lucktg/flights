package com.nearsoft.flights.data.getters;
import java.util.Set;

import com.nearsoft.flights.vo.AirportDTO;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.TripInformationRequest;

public interface TravelDataGetter {
	
	Set<AirportDTO> getAllActiveAirports();
	Set<Flight> getDepartingFlightsByRouteNDate(TripInformationRequest tripInformation);
}
