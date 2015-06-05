package com.nearsoft.flights.domain.services;

import java.util.Set;

import org.apache.log4j.Logger;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.ehcache.annotations.Cacheable;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.FlightRepository;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;
import com.nearsoft.flights.interfaces.FlightFlexApiService;

@Service
public class FlightsServiceImpl implements FlightsService {
	
	private static final Logger logger = Logger.getLogger(FlightsServiceImpl.class);
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private FlightFlexApiService flightApiService;
	

	@Override
	public RoundTrip getRoundTripFlights(TripInformationRequest origin, TripInformationRequest destiny) {
		Set<Flight> originFlights = getOneWayFlights(origin);
		Set<Flight> destinyFlights = getOneWayFlights(destiny);
		RoundTrip roundTrip = new RoundTrip(originFlights, destinyFlights);
		return roundTrip;
	}

	@Cacheable(cacheName="flights")
	@Override
	public Set<Flight> getOneWayFlights(TripInformationRequest origin) {
		logger.debug("Entering method getOneWayFlights, means no using cache");
		Set<Flight> flights = null;
		if((flights = flightRepository.findFlightsByDeparture(origin)) == null || flights.isEmpty()){
			flightRepository.add(flights = flightApiService.getDepartingFlightsByTripInformation(origin));
		}
		return flights;
	}
}
