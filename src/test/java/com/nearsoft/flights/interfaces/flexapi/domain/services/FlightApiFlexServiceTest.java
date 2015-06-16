package com.nearsoft.flights.interfaces.flexapi.domain.services;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

import com.nearsoft.flights.domain.model.Flight;
import com.nearsoft.flights.domain.model.TripInformationRequest;
import com.nearsoft.flights.interfaces.FlightApiService;
import com.nearsoft.flights.interfaces.flexapi.domain.services.FlightFlexApiServiceImpl.URLParams;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class FlightApiFlexServiceTest {
	
	private static final String MEXICO_AIRPORT_CODE = "MX";

	private static final int ONE = 1;

	@Autowired
	@InjectMocks
	private FlightApiService flightApiService;
	
	@Mock
	private RestServiceInvoker restServiceInvoker;
	
	@Value("#{apiConfig}")
	private Map<String,String> apiConfigs;
	
	
	public FlightApiFlexServiceTest() {
		BasicConfigurator.configure();
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldReturnNullFlightSet() {
		when(restServiceInvoker.invoke(any(), any(), any())).thenReturn(null);
		Calendar calendar = Calendar.getInstance();
		Set<Flight> flights = executeGetFlights(calendar.getTime());
		Map<String, String> urlParams = getUrlParams(calendar);
		Assert.assertNull(flights);
		verify(restServiceInvoker, times(1)).invoke(
				eq(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(apiConfigs, urlParams)), 
				any(), 
				eq(FlightJsonSetWrapper.class));
	}
	
	@Test
	public void shouldReturnEmptyAirportSet() {
		when(restServiceInvoker.invoke(any(), any(), any())).thenReturn(Collections.emptySet());
		Calendar calendar = Calendar.getInstance();
		Map<String, String> urlParams = getUrlParams(calendar);
		Set<Flight> flights = executeGetFlights(calendar.getTime());
		Assert.assertNotNull(flights);
		Assert.assertThat(flights, is(empty()));
		verify(restServiceInvoker, times(1)).invoke(
				eq(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(apiConfigs, urlParams)), 
				any(), 
				eq(FlightJsonSetWrapper.class));
	}
	
	@Test
	public void shouldReturnOneElementFlightSet() {
		when(restServiceInvoker.invoke(any(), any(), any())).thenReturn(Collections.singleton(Flight.emptyFlight()));
		Calendar calendar = Calendar.getInstance();
		Map<String, String> urlParams = getUrlParams(calendar);
		Set<Flight> flights = executeGetFlights(calendar.getTime());
		Assert.assertNotNull(flights);
		Assert.assertThat(flights, is(not(empty())));
		Assert.assertThat(flights.size(), is(ONE));
		verify(restServiceInvoker, times(1)).invoke(
				eq(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(apiConfigs, urlParams)), 
				any(), 
				eq(FlightJsonSetWrapper.class));
	}

	private Map<String, String> getUrlParams(Calendar calendar) {
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(URLParams.arrivalAirportCode.name(), MEXICO_AIRPORT_CODE);
		urlParams.put(URLParams.day.name(), calendar.get(Calendar.DAY_OF_MONTH) +"");
		urlParams.put(URLParams.month.name(), (calendar.get(Calendar.MONTH) + 1) +"");
		urlParams.put(URLParams.year.name(), calendar.get(Calendar.YEAR) +"");
		urlParams.put(URLParams.departureAirportCode.name(), MEXICO_AIRPORT_CODE);
		return urlParams;
	}

	private Set<Flight> executeGetFlights(Date date) {
		Set<Flight> flights = flightApiService.getDepartingFlightsByTripInformation(
				new TripInformationRequest(MEXICO_AIRPORT_CODE,date,MEXICO_AIRPORT_CODE));
		return flights;
	}
	
}
