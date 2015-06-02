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

import com.nearsoft.flights.persistence.dao.AirportDao;
import com.nearsoft.flights.persistence.dao.FlightDao;
import com.nearsoft.flights.persistence.dto.TripInformationRequestDto;
import com.nearsoft.flights.vo.AirportDTO;
import com.nearsoft.flights.vo.FlightDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class DatabaseTravelDataGetterTest {
	
	private static final String HMO_AIRPORT_CODE = "HMO";

	private static final String MEX_AIRPORT_CODE = "MEX";

	DatabaseTravelDataGetter databaseTravelDataGetter;
	
	@Mock
	private AirportDao airportDAO;
	
	@Mock
	private FlightDao flightDAO;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		databaseTravelDataGetter = new DatabaseTravelDataGetter(airportDAO, flightDAO);
	}
	
	@Test
	public void shouldReturnNullAirportSet() {
		when(airportDAO.findActiveAirports()).thenReturn(null);
		
		Set<AirportDto> airports = databaseTravelDataGetter.getAllActiveAirports();
		Assert.assertNull(airports);
		
		verify(airportDAO).findActiveAirports();
	}
	
	@Test
	public void shouldReturnEmptyAirportSet() {
		when(airportDAO.findActiveAirports()).thenReturn(Collections.emptySet());
		
		Set<AirportDto> airports = databaseTravelDataGetter.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(empty()));
		
		verify(airportDAO).findActiveAirports();
	}
	
	@Test
	public void shouldReturnNotEmptyAirportSet() {
		when(airportDAO.findActiveAirports()).thenReturn(getAirportSet());
		
		Set<AirportDto> airports = databaseTravelDataGetter.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(not(empty())));
		
		verify(airportDAO).findActiveAirports();
	}
	
	
	
	@Test
	public void shouldReturnNullFlightSet() {
		TripInformationRequestDto tripInformation = getTripInformation();
		when(flightDAO.findDepartingFlightsByRouteNDate(tripInformation)).thenReturn(null);
		
		Set<FlightDto> flights = databaseTravelDataGetter.getDepartingFlightsByRouteNDate(tripInformation);
		Assert.assertNull(flights);
		
		verify(flightDAO).findDepartingFlightsByRouteNDate(tripInformation);
	}
	
	@Test
	public void shouldReturnEmptyFlightSet() {
		TripInformationRequestDto tripInformation = getTripInformation();
		when(flightDAO.findDepartingFlightsByRouteNDate(tripInformation)).thenReturn(Collections.emptySet());
		
		Set<FlightDto> flights = databaseTravelDataGetter.getDepartingFlightsByRouteNDate(tripInformation);
		Assert.assertNotNull(flights);
		Assert.assertThat(flights, is(empty()));
		
		verify(flightDAO).findDepartingFlightsByRouteNDate(tripInformation);
	}
	
	@Test
	public void shouldReturnNotEmptyFlighttSet() {
		TripInformationRequestDto tripInformation = getTripInformation();
		when(flightDAO.findDepartingFlightsByRouteNDate(tripInformation)).thenReturn(getFlightSet());
		
		Set<FlightDto> flights = databaseTravelDataGetter.getDepartingFlightsByRouteNDate(tripInformation);
		Assert.assertNotNull(flights);
		Assert.assertThat(flights, is(not(empty())));
		
		verify(flightDAO).findDepartingFlightsByRouteNDate(tripInformation);
	}
	
	private Set<FlightDto> getFlightSet() {
		Set<FlightDto> flights = new HashSet<FlightDto>();
		flights.add(new FlightDto());
		return flights;
	}

	private Set<AirportDto> getAirportSet() {
		Set<AirportDto> airports = new HashSet<AirportDto>();
		airports.add(new AirportDto());
		return airports;
	}
	
	private TripInformationRequestDto getTripInformation() {
		return new TripInformationRequestDto(MEX_AIRPORT_CODE, HMO_AIRPORT_CODE, new Date());
	}
}
