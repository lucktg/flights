package com.nearsoft.flights.interfaces.flexapi.domain.services;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.AirportApiService;
import com.nearsoft.flights.interfaces.flexapi.domain.services.AirportApiFlexService.URLParams;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class AirportApliFlexServiceTest {
	
	private static final String MEXICO_AIRPORT_CODE = "MX";

	private static final int ONE = 1;

	@Autowired
	@InjectMocks
	private AirportApiService airportApiService;
	
	@Mock
	private RestServiceInvoker restServiceInvoker;
	
	@Value("#{apiConfig}")
	private Map<String,String> apiConfigs;
	
	
	public AirportApliFlexServiceTest() {
		BasicConfigurator.configure();
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldReturnNullAirportSet() {
		when(restServiceInvoker.invoke(any(), any(), any())).thenReturn(null);
		Set<Airport> airports = airportApiService.getAllActiveAirports();
		Assert.assertNull(airports);
		verify(restServiceInvoker, times(1)).invoke(
				eq(UriUtils.buildAllActiveAirportsJSONURI(apiConfigs)), 
				any(), 
				eq(AirportJsonSetWrapper.class));
	}
	
	@Test
	public void shouldReturnEmptyAirportSet() {
		when(restServiceInvoker.invoke(any(), any(), any())).thenReturn(Collections.emptySet());
		Set<Airport> airports = airportApiService.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(empty()));
		verify(restServiceInvoker, times(1)).invoke(
				eq(UriUtils.buildAllActiveAirportsJSONURI(apiConfigs)), 
				any(), 
				eq(AirportJsonSetWrapper.class));
	}
	
	@Test
	public void shouldReturnOneElementAirportSet() {
		when(restServiceInvoker.invoke(any(), any(), any())).thenReturn(Collections.singleton(Airport.emptyAirport()));
		Set<Airport> airports = airportApiService.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(not(empty())));
		Assert.assertThat(airports.size(), is(ONE));
		verify(restServiceInvoker, times(1)).invoke(
				eq(UriUtils.buildAllActiveAirportsJSONURI(apiConfigs)), 
				any(), 
				eq(AirportJsonSetWrapper.class));
	}
	
	@Test
	public void shouldReturnNullAirport() {
		when(restServiceInvoker.invoke(any(), any(), any())).thenReturn(null);
		Airport airport = airportApiService.getAirportByCode(MEXICO_AIRPORT_CODE);
		Map<String,String> urlParams = new HashMap<>();
		urlParams.put(URLParams.code.name(), MEXICO_AIRPORT_CODE);
		Assert.assertNull(airport);
		verify(restServiceInvoker, times(1)).invoke(
				eq(UriUtils.buildAirportsJSONURI(apiConfigs, urlParams)), 
				any(), 
				eq(AirportJsonWrapper.class));
	}
	
	
	@Test
	public void shouldReturnAirport() {
		when(restServiceInvoker.invoke(any(), any(), any())).thenReturn(Airport.emptyAirport());
		Airport airport = airportApiService.getAirportByCode(MEXICO_AIRPORT_CODE);
		Map<String,String> urlParams = new HashMap<>();
		urlParams.put(URLParams.code.name(), MEXICO_AIRPORT_CODE);
		Assert.assertNotNull(airport);
		verify(restServiceInvoker, times(1)).invoke(
				eq(UriUtils.buildAirportsJSONURI(apiConfigs, urlParams)), 
				any(), 
				eq(AirportJsonWrapper.class));
	}
	
	@Test
	public void shouldThrowIllegalArgumentException() {
		when(restServiceInvoker.invoke(any(), any(), any())).thenReturn(Airport.emptyAirport());
		Airport airport = airportApiService.getAirportByCode(MEXICO_AIRPORT_CODE);
		Map<String,String> urlParams = new HashMap<>();
		urlParams.put(URLParams.code.name(), MEXICO_AIRPORT_CODE);
		Assert.assertNotNull(airport);
		verify(restServiceInvoker, times(1)).invoke(
				eq(UriUtils.buildAirportsJSONURI(apiConfigs, urlParams)), 
				any(), 
				eq(AirportJsonWrapper.class));
	}
}
