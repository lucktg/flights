package com.nearsoft.flights.service;

import java.util.Set;

import com.nearsoft.flights.data.getters.TravelDataGetter;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.RoundTrip;
import com.nearsoft.flights.vo.TripInformationRequest;

public class FlightsServiceImpl implements FlightsService {
	private TravelDataGetter databaseTravelDataGetter;
	private TravelDataGetter apiClientTravelDataGetter;
	
	@Override
	public RoundTrip getRoundTripFlights(TripInformationRequest origin, TripInformationRequest destiny) {
		Set<Flight> originFlights = getOneWayFlights(origin);
		Set<Flight> destinyFlights = getOneWayFlights(destiny);
		RoundTrip roundTrip = new RoundTrip(originFlights, destinyFlights);
		return roundTrip;
	}

	public Set<Flight> getOneWayFlights(TripInformationRequest origin) {
		Set<Flight> flights = databaseTravelDataGetter.getDepartingFlightsByRouteNDate(origin);
		if (flights == null || flights.isEmpty()) {
			flights =apiClientTravelDataGetter.getDepartingFlightsByRouteNDate(origin);
		}
		return flights;
	}
}
