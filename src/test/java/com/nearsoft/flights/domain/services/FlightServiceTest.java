package com.nearsoft.flights.domain.services;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.domain.model.flight.Airline;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.Flight.FlightBuilder;
import com.nearsoft.flights.domain.model.flight.ScheduledTrip;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;
import com.nearsoft.flights.domain.model.repository.Repository;
import com.nearsoft.flights.interfaces.FlightApiService;
@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {
	private static final Logger logger = Logger.getLogger(FlightServiceTest.class);
	
	@Autowired
	@InjectMocks
	FlightsServiceImpl flightsService;
	
	@Mock
	private Repository<Flight> flightRepository;
	
	@Mock
	private Repository<Airport> airportRepository;
	
	@Mock
	private Repository<Airline> airlineRepository;
	
	@Mock
	private FlightApiService flightApiService;
	
	public FlightServiceTest() {
		BasicConfigurator.configure();
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldReturnNullAirportSet() {
		when(flightRepository.getAllBySpecification(any())).thenReturn(null);
		when(flightApiService.getDepartingFlightsByTripInformation(any())).thenReturn(null);
		Set<Flight> flights = flightsService.getOneWayFlights(any());
		Assert.assertNull(flights);
		verify(flightRepository, times(1)).getAllBySpecification(any());
		verify(flightApiService, times(1)).getDepartingFlightsByTripInformation(any());
		verifyZeroInteractions(airportRepository,airlineRepository);
		verifyNoMoreInteractions(flightRepository,flightApiService);
	}
	
	
	@Test
	public void shouldGetAirportSetFromRepository() {
		when(flightRepository.getAllBySpecification(any())).thenReturn(Collections.singletonList(getFlight()));
		Set<Flight> flights = flightsService.getOneWayFlights(new TripInformationRequest("MEX", new Date(), "GDL"));
		Assert.assertNotNull(flights);
		Assert.assertThat(flights, is(not(empty())));
		verifyZeroInteractions(flightApiService);
		verify(flightRepository, times(1)).getAllBySpecification(any());
		verify(airlineRepository, times(1)).getBySpecification(any());
		verify(airportRepository, times(2)).getBySpecification(any());
	}
	
	@Test
	public void shouldGetFlightSetFromApi() {
		when(flightRepository.getAllBySpecification(any())).thenReturn(null);
		when(flightApiService.getDepartingFlightsByTripInformation(any())).thenReturn(Collections.singleton(getFlight()));
		Set<Flight> flights = flightsService.getOneWayFlights(any());
		Assert.assertNotNull(flights);
		Assert.assertThat(flights, is(not(empty())));
		verify(flightRepository, times(1)).getAllBySpecification(any());
		verify(flightApiService, times(1)).getDepartingFlightsByTripInformation(any());
		Set<Airport> airports = flights.stream().map(p -> p.getDeparture().getAirport()).collect(Collectors.toSet());
		airports.addAll(flights.stream().map(p -> p.getArrival().getAirport()).collect(Collectors.toSet()));
		verify(airportRepository, times(1)).addAll(airports);
		verify(airlineRepository, times(1)).addAll(flights.stream().map(p -> p.getAirline()).collect(Collectors.toSet()));
		verify(flightRepository, times(1)).addAll(flights);
	}

	
	private Flight getFlight() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 5);
		FlightBuilder flightBuilder = new FlightBuilder("804",new Airline("AIR1", null, null, null));
		AirportBuilder arrivalBuilder = new AirportBuilder("1");
		AirportBuilder departureBuilder = new AirportBuilder("2");
		flightBuilder.addFlightRoute(new ScheduledTrip(departureBuilder.build(), new Date(), "T2"),
				new ScheduledTrip(arrivalBuilder.build(), new Date(calendar.getTimeInMillis()),"T1"));
		flightBuilder.addServiceType("K");
		return flightBuilder.build();
	}
}
