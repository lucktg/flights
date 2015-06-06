package com.nearsoft.flights.domain.services;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.FlightRepository;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;
import com.nearsoft.flights.interfaces.FlightApiService;

@Service
public class FlightsServiceImpl implements FlightsService {
	
	private static final Logger logger = Logger.getLogger(FlightsServiceImpl.class);
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private FlightApiService flightApiService;
	

	@Override
	public RoundTrip getRoundTripFlights(TripInformationRequest origin, TripInformationRequest destiny) {
		logger.debug("Getting round trip flights");
		Set<Flight> originFlights = getOneWayFlights(origin);
		Set<Flight> destinyFlights = getOneWayFlights(destiny);
		RoundTrip roundTrip = new RoundTrip(originFlights, destinyFlights);
		return roundTrip;
	}

	@Cacheable(cacheName="flights")
	@Override
	public Set<Flight> getOneWayFlights(TripInformationRequest origin) {
		logger.debug("Getting one way flights, means no using cache");
		Set<Flight> flights = null;
		if((flights = flightRepository.findDepartingFlightsByTripInformationRequest(origin)) == null || flights.isEmpty()){
			logger.debug("Getting one way flights from flight API service");
			flightRepository.add(flights = flightApiService.getDepartingFlightsByTripInformation(origin));
		}
		return flights;
	}
}
