package com.nearsoft.flights.data.getters;
import java.util.Set;

import com.nearsoft.flights.persistence.dto.TripInformationRequestDto;
import com.nearsoft.flights.vo.AirportDTO;
import com.nearsoft.flights.vo.FlightDto;

public interface TravelDataGetter {
	
	Set<AirportDTO> getAllActiveAirports();
	Set<FlightDto> getDepartingFlightsByRouteNDate(TripInformationRequestDto tripInformation);
}
