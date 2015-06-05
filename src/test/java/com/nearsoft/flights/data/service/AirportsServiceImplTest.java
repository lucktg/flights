package com.nearsoft.flights.data.service;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observer;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nearsoft.flights.data.getters.APIClientTravelDataGetter;
import com.nearsoft.flights.data.getters.DatabaseTravelDataGetter;
import com.nearsoft.flights.interfaces.TravelAPIClient;
import com.nearsoft.flights.persistence.dao.AirportDao;
import com.nearsoft.flights.persistence.dao.FlightDao;
import com.nearsoft.flights.service.AirportsService;
import com.nearsoft.flights.service.AirportsServiceImpl;
import com.nearsoft.flights.vo.AirportDTO;
import com.nearsoft.flights.vo.Airports;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class AirportsServiceImplTest {
	
	private AirportsService airportService;
		
	@Mock
	private AirportDao airportDAO;
	
	@Mock
	private FlightDao flightDAO;
	
	@Mock
	private TravelAPIClient apiClient;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		DatabaseTravelDataGetter dbGetter = new DatabaseTravelDataGetter(airportDAO, flightDAO);
		List<Observer> observers = new ArrayList<Observer>();
		observers.add(dbGetter);
		airportService = new AirportsServiceImpl(new APIClientTravelDataGetter(apiClient, observers), dbGetter);
	}
	
	@Test
	public void testEmptyAirportSetForBothGetters() {
		when(airportDAO.findActiveAirports()).thenReturn(null);
		when(apiClient.getAllActiveAirports()).thenReturn(null);
		
		Set<Airport> airports = airportService.getActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(empty()));
		
		verify(airportDAO).findActiveAirports();
		verify(apiClient).getAllActiveAirports();
		verifyNoMoreInteractions(apiClient, flightDAO);
		verify(airportDAO, never()).updateAirports(any());
		verifyNoMoreInteractions(airportDAO);
		
	}
	
	@Test
	public void testEmptyAirportSetForDatabaseButEmptySetForApiGetter() {
		when(airportDAO.findActiveAirports()).thenReturn(null);
		when(apiClient.getAllActiveAirports()).thenReturn(getAirportsWithEmptyAirportSet());
		
		Set<Airport> airports = airportService.getActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(empty()));
		
		verifyZeroInteractions(flightDAO);
		verify(airportDAO).findActiveAirports();
		verify(apiClient).getAllActiveAirports();
		verifyNoMoreInteractions(apiClient);
		verify(airportDAO).updateAirports(any());
		verifyNoMoreInteractions(airportDAO);
	}
	
	@Test
	public void testNotEmptyAirportSetForDatabaseGetter() {
		when(airportDAO.findActiveAirports()).thenReturn(getAirportSet());
		when(apiClient.getAllActiveAirports()).thenReturn(null);
		
		Set<Airport> airports = airportService.getActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(not(empty())));
		
		verify(airportDAO).findActiveAirports();
		verify(apiClient, never()).getAllActiveAirports();
		verify(airportDAO, never()).updateAirports(any());
		verifyZeroInteractions(apiClient, flightDAO);
		verifyNoMoreInteractions(airportDAO);
	}
	
	@Test
	public void testEmptyAirportSetForDatabaseButNotEmptySetForApiGetter() {
		when(airportDAO.findActiveAirports()).thenReturn(null);
		when(apiClient.getAllActiveAirports()).thenReturn(getAirports());
		
		Set<Airport> airports = airportService.getActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(not(empty())));
		
		verify(airportDAO).findActiveAirports();
		verify(apiClient).getAllActiveAirports();
		verify(airportDAO).updateAirports(any());
		verifyZeroInteractions(flightDAO);
		verifyNoMoreInteractions(airportDAO);
	}

	private Airports getAirportsWithEmptyAirportSet() {
		return new Airports(null);
	}
	
	private Airports getAirports() {
		return new Airports(Collections.singleton(new Airport()));
	}
	
	private Set<Airport> getAirportSet() {
		Set<Airport> airports = Collections.singleton(new Airport());
		return airports;
	}
}
