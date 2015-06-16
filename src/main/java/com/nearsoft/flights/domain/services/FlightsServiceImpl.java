package com.nearsoft.flights.domain.services;

import static com.nearsoft.flights.util.Utils.isNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.nearsoft.flights.domain.model.Airline;
import com.nearsoft.flights.domain.model.Airport;
import com.nearsoft.flights.domain.model.Flight;
import com.nearsoft.flights.domain.model.Flight.FlightBuilder;
import com.nearsoft.flights.domain.model.RoundTrip;
import com.nearsoft.flights.domain.model.ScheduledTrip;
import com.nearsoft.flights.domain.model.TripInformationRequest;
import com.nearsoft.flights.domain.repository.AirlineRepository;
import com.nearsoft.flights.domain.repository.AirportRepository;
import com.nearsoft.flights.domain.repository.FlightRepository;
import com.nearsoft.flights.interfaces.FlightApiService;
@Service("flightService")
public class FlightsServiceImpl implements FlightsService {
	
	private static final Logger logger = Logger.getLogger(FlightsServiceImpl.class);
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private AirportRepository airportRepository;
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	@Autowired
	private FlightApiService flightApiService;
	
	@Transactional
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
	@Transactional
	public Set<Flight> getOneWayFlights(TripInformationRequest origin) {
		logger.debug("Getting one way flights, means not using cache");
		List<Flight> flights = flightRepository.getBytTripInformation(origin);
		Set<Flight> flightsSet = Collections.emptySet();
		if(flights  == null || flights.isEmpty()) {
			logger.debug("Getting one way flights from flight API service");
			flightsSet = flightApiService.getDepartingFlightsByTripInformation(origin);
			if(isNotNull(flightsSet)) {
				Set<Airline> airlines = flightsSet.stream().map(p -> p.getAirline()).collect(Collectors.toSet());
				Set<Airport> airports = flightsSet.stream().map(p -> p.getArrival().getAirport()).collect(Collectors.toSet());
				airports.addAll(flightsSet.stream().map(p -> p.getDeparture().getAirport()).collect(Collectors.toSet()));
				airportRepository.addAll(airports);
				airlineRepository.addAll(airlines);
				flightRepository.addAll(flightsSet);
			}
		} else {
			flightsSet = flights.stream().map(p-> {
				Airline airline = Airline.emptyAirline();
				Airport arrival = Airport.emptyAirport();
				Airport departure = Airport.emptyAirport();
				if(isNotNull(p.getAirline())) {
					airline = airlineRepository.getByAirlineCode(p.getAirline().getAirlineCode());
				}
				if(isNotNull(p.getDeparture()) && isNotNull(p.getDeparture().getAirport())) {
					departure = airportRepository.getByAirportCode(p.getDeparture().getAirport().getAirportCode());
				}
				
				if(isNotNull(p.getArrival()) && isNotNull(p.getArrival().getAirport())){
					arrival = airportRepository.getByAirportCode(p.getArrival().getAirport().getAirportCode());
				}
				FlightBuilder builder = new FlightBuilder(p.getFlightNumber(), airline);
				if(isNotNull(departure) && isNotNull(arrival)) {
					builder.addFlightRoute(new ScheduledTrip(departure, p.getDeparture().getScheduledDate(), p.getDeparture().getTerminal()), 
							new ScheduledTrip(arrival, p.getArrival().getScheduledDate(), p.getArrival().getTerminal()));
				} else {
					logger.warn("Departure or Arrival not found in repository, this  shouldn't happen ");
				}
				return builder.build();
			}).collect(Collectors.toSet());
		}
		return flightsSet;
	}
}
