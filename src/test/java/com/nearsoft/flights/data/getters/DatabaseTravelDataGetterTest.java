package com.nearsoft.flights.data.getters;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nearsoft.flights.persistence.dao.AirportDAO;
import com.nearsoft.flights.persistence.dao.FlightDAO;
import com.nearsoft.flights.vo.AirportDTO;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.TripInformationRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class DatabaseTravelDataGetterTest {
	
	private static final String HMO_AIRPORT_CODE = "HMO";

	private static final String MEX_AIRPORT_CODE = "MEX";

	DatabaseTravelDataGetter databaseTravelDataGetter;
	
	@Mock
	private AirportDAO airportDAO;
	
	@Mock
	private FlightDAO flightDAO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		databaseTravelDataGetter = new DatabaseTravelDataGetter(airportDAO, flightDAO);
	}
	
	@Test
	public void shouldReturnNullAirportSet() {
		when(airportDAO.findActiveAirports()).thenReturn(null);
		
		Set<AirportDTO> airports = databaseTravelDataGetter.getAllActiveAirports();
		Assert.assertNull(airports);
		
		verify(airportDAO).findActiveAirports();
	}
	
	@Test
	public void shouldReturnEmptyAirportSet() {
		when(airportDAO.findActiveAirports()).thenReturn(Collections.emptySet());
		
		Set<AirportDTO> airports = databaseTravelDataGetter.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(empty()));
		
		verify(airportDAO).findActiveAirports();
	}
	
	@Test
	public void shouldReturnNotEmptyAirportSet() {
		when(airportDAO.findActiveAirports()).thenReturn(getAirportSet());
		
		Set<AirportDTO> airports = databaseTravelDataGetter.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(not(empty())));
		
		verify(airportDAO).findActiveAirports();
	}
	
	
	
	@Test
	public void shouldReturnNullFlightSet() {
		TripInformationRequest tripInformation = getTripInformation();
		when(flightDAO.findDepartingFlightsByRouteNDate(tripInformation)).thenReturn(null);
		
		Set<Flight> flights = databaseTravelDataGetter.getDepartingFlightsByRouteNDate(tripInformation);
		Assert.assertNull(flights);
		
		verify(flightDAO).findDepartingFlightsByRouteNDate(tripInformation);
	}
	
	@Test
	public void shouldReturnEmptyFlightSet() {
		TripInformationRequest tripInformation = getTripInformation();
		when(flightDAO.findDepartingFlightsByRouteNDate(tripInformation)).thenReturn(Collections.emptySet());
		
		Set<Flight> flights = databaseTravelDataGetter.getDepartingFlightsByRouteNDate(tripInformation);
		Assert.assertNotNull(flights);
		Assert.assertThat(flights, is(empty()));
		
		verify(flightDAO).findDepartingFlightsByRouteNDate(tripInformation);
	}
	
	@Test
	public void shouldReturnNotEmptyFlighttSet() {
		TripInformationRequest tripInformation = getTripInformation();
		when(flightDAO.findDepartingFlightsByRouteNDate(tripInformation)).thenReturn(getFlightSet());
		
		Set<Flight> flights = databaseTravelDataGetter.getDepartingFlightsByRouteNDate(tripInformation);
		Assert.assertNotNull(flights);
		Assert.assertThat(flights, is(not(empty())));
		
		verify(flightDAO).findDepartingFlightsByRouteNDate(tripInformation);
	}
	
	private Set<Flight> getFlightSet() {
		Set<Flight> flights = new HashSet<Flight>();
		flights.add(new Flight());
		return flights;
	}

	private Set<AirportDTO> getAirportSet() {
		Set<AirportDTO> airports = new HashSet<AirportDTO>();
		airports.add(new AirportDTO());
		return airports;
	}
	
	private TripInformationRequest getTripInformation() {
		return new TripInformationRequest(MEX_AIRPORT_CODE, HMO_AIRPORT_CODE, new Date());
	}
}
