package com.nearsoft.flights.service;

import java.util.Set;

import com.nearsoft.flights.data.getters.TravelDataGetter;
import com.nearsoft.flights.persistence.dto.TripInformationRequestDto;
import com.nearsoft.flights.vo.FlightDto;
import com.nearsoft.flights.vo.RoundTrip;

public class FlightsServiceImpl implements FlightsService {
	private TravelDataGetter databaseTravelDataGetter;
	private TravelDataGetter apiClientTravelDataGetter;
	
	@Override
	public RoundTrip getRoundTripFlights(TripInformationRequestDto origin, TripInformationRequestDto destiny) {
		Set<FlightDto> originFlights = getOneWayFlights(origin);
		Set<FlightDto> destinyFlights = getOneWayFlights(destiny);
		RoundTrip roundTrip = new RoundTrip(originFlights, destinyFlights);
		return roundTrip;
	}

	public Set<FlightDto> getOneWayFlights(TripInformationRequestDto origin) {
		Set<FlightDto> flights = databaseTravelDataGetter.getDepartingFlightsByRouteNDate(origin);
		if (flights == null || flights.isEmpty()) {
			flights =apiClientTravelDataGetter.getDepartingFlightsByRouteNDate(origin);
		}
		return flights;
	}
}
