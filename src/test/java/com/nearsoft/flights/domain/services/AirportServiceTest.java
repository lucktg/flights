package com.nearsoft.flights.domain.services;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.Set;

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
import com.nearsoft.flights.domain.model.repository.Repository;
import com.nearsoft.flights.interfaces.AirportApiService;
@RunWith(MockitoJUnitRunner.class)
public class AirportServiceTest {
	private static final Logger logger = Logger.getLogger(AirportServiceTest.class);
	
	@Autowired
	@InjectMocks
	AirportsServiceImpl airportsService;
	
	@Mock
	private Repository<Airport> airportRepository;
	@Mock
	private AirportApiService airportApiService;
	
	public AirportServiceTest() {
		BasicConfigurator.configure();
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldReturnNullAirportSet() {
		when(airportRepository.getAll()).thenReturn(null);
		when(airportApiService.getAllActiveAirports()).thenReturn(null);
		Set<Airport> airports = airportsService.getActiveAirports();
		Assert.assertNull(airports);
		verify(airportRepository, times(1)).getAll();
		verify(airportApiService, times(1)).getAllActiveAirports();
	}
	
	@Test
	public void shouldGetAirportSetFromRepository() {
		when(airportRepository.getAll()).thenReturn(Collections.singletonList(Airport.emptyAirport()));
		Set<Airport> airports = airportsService.getActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(not(empty())));
		verify(airportRepository, times(1)).getAll();
		verify(airportApiService, times(0)).getAllActiveAirports();
	}
	
	@Test
	public void shouldGetAirportSetFromApi() {
		when(airportRepository.getAll()).thenReturn(null);
		when(airportApiService.getAllActiveAirports()).thenReturn(Collections.singleton(Airport.emptyAirport()));
		Set<Airport> airports = airportsService.getActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(not(empty())));
		verify(airportRepository, times(1)).getAll();
		verify(airportApiService, times(1)).getAllActiveAirports();
		verify(airportRepository, times(1)).addAll(airports);
		verifyNoMoreInteractions(airportRepository, airportApiService);
	}

}
