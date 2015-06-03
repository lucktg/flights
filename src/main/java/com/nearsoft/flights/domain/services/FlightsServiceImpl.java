package com.nearsoft.flights.domain.services;

import java.util.Set;

import com.nearsoft.flights.domain.model.exception.RepositoryException;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.FlightRepository;
import com.nearsoft.flights.domain.model.flight.RoundTrip;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;
import com.nearsoft.flights.domain.services.exception.ServiceException;

public class FlightsServiceImpl implements FlightsService {
	
	private FlightRepository flightRepository;
	
	
	
	public FlightsServiceImpl(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	@Override
	public RoundTrip getRoundTripFlights(TripInformationRequest origin, TripInformationRequest destiny) throws ServiceException {
		Set<Flight> originFlights = getOneWayFlights(origin);
		Set<Flight> destinyFlights = getOneWayFlights(destiny);
		RoundTrip roundTrip = new RoundTrip(originFlights, destinyFlights);
		return roundTrip;
	}

	@Override
	public Set<Flight> getOneWayFlights(TripInformationRequest origin) throws ServiceException {
		try {
			return flightRepository.findFlightsByDeparture(origin);
		} catch (RepositoryException e) {
			throw new ServiceException("Error occurred while gathering flight information", e);
		}
	}
}
