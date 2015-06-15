package com.nearsoft.flights.domain.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.flight.Airline;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.Flight.FlightBuilder;
import com.nearsoft.flights.domain.model.flight.ScheduledTrip;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;
import com.nearsoft.flights.domain.model.repository.Repository;
import com.nearsoft.flights.domain.model.repository.jdbc.specification.AirlineSpecificationByCode;
import com.nearsoft.flights.domain.model.repository.jdbc.specification.AirportSpecificationByCode;
import com.nearsoft.flights.domain.model.repository.jdbc.specification.FlightSpecificationByTripInformation;
import com.nearsoft.flights.interfaces.FlightApiService;

@Service
public class FlightsServiceImpl implements FlightsService {
	
	private static final Logger logger = Logger.getLogger(FlightsServiceImpl.class);
	
	@Autowired
	private Repository<Flight> flightRepository;
	
	@Autowired
	private Repository<Airport> airportRepository;
	
	@Autowired
	private Repository<Airline> airlineRepository;
	
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
		logger.debug("Getting one way flights, means not using cache");
		FlightSpecificationByTripInformation specification = new FlightSpecificationByTripInformation(origin);
		List<Flight> flights = flightRepository.getAllBySpecification(specification);
		Set<Flight> flightsSet = Collections.emptySet();
		if(flights  == null || flights.isEmpty()) {
			logger.debug("Getting one way flights from flight API service");
			flightsSet = flightApiService.getDepartingFlightsByTripInformation(origin);
			Set<Airline> airlines = flightsSet.stream().map(p -> p.getAirline()).collect(Collectors.toSet());
			Set<Airport> airports = flightsSet.stream().map(p -> p.getArrival().getAirport()).collect(Collectors.toSet());
			airports.addAll(flightsSet.stream().map(p -> p.getDeparture().getAirport()).collect(Collectors.toSet()));
			airportRepository.addAll(airports);
			airlineRepository.addAll(airlines);
			flightRepository.addAll(flightsSet);
		} else {
			flightsSet = flights.stream().map(p-> {
				Airline airline = airlineRepository.getBySpecification(new AirlineSpecificationByCode(p.getAirline().getAirlineCode()));
				FlightBuilder builder = new FlightBuilder(p.getFlightNumber(), airline);
				Airport departure = airportRepository.getBySpecification(new AirportSpecificationByCode(p.getDeparture().getAirport().getAirportCode()));
				Airport arrival = airportRepository.getBySpecification(new AirportSpecificationByCode(p.getArrival().getAirport().getAirportCode()));
				builder.addFlightRoute(new ScheduledTrip(departure, p.getDeparture().getScheduledDate(), p.getDeparture().getTerminal()), 
						new ScheduledTrip(arrival, p.getArrival().getScheduledDate(), p.getArrival().getTerminal()));
				return builder.build();
			}).collect(Collectors.toSet());
		}
		return flightsSet;
	}
}
