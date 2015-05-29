package com.nearsoft.flights.client;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jersey.repackaged.com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.nearsoft.flights.interfaces.TravelAPIClient;
import com.nearsoft.flights.interfaces.flexapi.TravelFlightStatsClient;
import com.nearsoft.flights.rest.util.UriUtils;
import com.nearsoft.flights.vo.Airport;
import com.nearsoft.flights.vo.Airports;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.Flights;
import com.nearsoft.flights.vo.TripInformationRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class TravelAPIClientTest {

	private static final String HMO_AIRPORT_CODE = "HMO";

	private static final String MEX_AIRPORT_CODE = "MEX";

	@InjectMocks
	@Autowired
	private TravelAPIClient client;
	
	@Mock
	private RestTemplate mockTemplate;
	
	@Mock
	private Map<String,String> mockAppKeys;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldReturnNotEmptyAirportsSet(){
		when(mockTemplate.exchange(UriUtils.buildAllActiveAirportsJSONURI(mockAppKeys), 
				HttpMethod.GET, 
				getJSONRequest(Airports.class), 
				Airports.class)).thenReturn(getAirportsResponseEntity());
		Airports airports = client.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports.getItems(), is(not(empty())));
		verify(mockTemplate).exchange(UriUtils.buildAllActiveAirportsJSONURI(mockAppKeys), HttpMethod.GET, 
				getJSONRequest(Airports.class), 
				Airports.class);
	} 
	
	@Test
	public void shouldReturnNullAirportSet(){
		when(mockTemplate.exchange(UriUtils.buildAllActiveAirportsJSONURI(mockAppKeys), 
				HttpMethod.GET, 
				getJSONRequest(Airports.class), 
				Airports.class)).thenReturn(null);
		
		Airports airports = client.getAllActiveAirports();
		Assert.assertNull(airports);
		verify(mockTemplate).exchange(UriUtils.buildAllActiveAirportsJSONURI(mockAppKeys), HttpMethod.GET, 
				getJSONRequest(Airports.class), 
				Airports.class);
	}
	
	@Test
	public void shouldReturnNotEmptyDepartingFlightsSet() {
		TripInformationRequest tripInformation = getTripInformation();
		when(mockTemplate.exchange(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(
				mockAppKeys,getFlighsByRouteNDateURLParamsMap(tripInformation.getDepartureDate())), 
				HttpMethod.GET, 
				getJSONRequest(Flights.class), 
				Flights.class)).thenReturn(getFlightsResponseEntity());
		
		Flights flights = client.getDepartingFlightsByRouteAndDate(tripInformation);
		Assert.assertNotNull(flights);
		Assert.assertThat(flights.getItems(), is(not(empty())));
		verify(mockTemplate).exchange(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(
				mockAppKeys,getFlighsByRouteNDateURLParamsMap(tripInformation.getDepartureDate())), 
				HttpMethod.GET, 
				getJSONRequest(Flights.class), 
				Flights.class);
	}
	
	@Test
	public void shouldReturnNullDepartingFlightsSet(){
		TripInformationRequest tripInformation = getTripInformation();
		when(mockTemplate.exchange(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(
				mockAppKeys,getFlighsByRouteNDateURLParamsMap(tripInformation.getDepartureDate())), 
				HttpMethod.GET,
				getJSONRequest(Flights.class), 
				Flights.class)).thenReturn(null);
		
		Flights flights = client.getDepartingFlightsByRouteAndDate(tripInformation);
		Assert.assertNull(flights);
		verify(mockTemplate).exchange(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(
				mockAppKeys,getFlighsByRouteNDateURLParamsMap(tripInformation.getDepartureDate())), 
				HttpMethod.GET, 
				getJSONRequest(Flights.class), 
				Flights.class);
	}
	
	@Test
	public void shouldFailWithException(){
		TripInformationRequest tripInformation = getTripInformation();
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(containsString("The template variable"));
		when(mockTemplate.exchange(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(mockAppKeys,null), 
				HttpMethod.GET,
				getJSONRequest(Flights.class), 
				Flights.class)).thenReturn(null);
		client.getDepartingFlightsByRouteAndDate(tripInformation);
	}
	
	@Test
	public void shouldReturnAirports() {
		TravelAPIClient newClient = getTravelAPIClientNoMocking();
		Airports airports = newClient.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports.getItems(), is(not(empty())));
	}
	
	@Test
	public void shouldReturnFlights() {
		TripInformationRequest tripInformation = getTripInformation();
		TravelAPIClient newClient = getTravelAPIClientNoMocking();
		Flights flights =newClient.getDepartingFlightsByRouteAndDate(tripInformation);
		Assert.assertNotNull(flights);
		Assert.assertThat(flights.getItems(), is(not(empty())));
	}
	
	private <E> HttpEntity<E> getJSONRequest(E e){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		HttpEntity<E> request = new HttpEntity<E>(null, headers);
		return request;
	}
	
	private ResponseEntity<Airports> getAirportsResponseEntity() {
		Airports airports  = new Airports(Collections.singleton(new Airport()));
		return new ResponseEntity<Airports>(airports, HttpStatus.OK);
	}
	
	private Map<String,String> getFlighsByRouteNDateURLParamsMap(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		Map<String,String> urlParams = new HashMap<String,String>();
		urlParams.put(TravelFlightStatsClient.URLParams.departureAirportCode.toString(), MEX_AIRPORT_CODE);
		urlParams.put(TravelFlightStatsClient.URLParams.arrivalAirportCode.toString(), HMO_AIRPORT_CODE);
		urlParams.put(TravelFlightStatsClient.URLParams.year.toString(), Integer.toString(calendar.get(Calendar.YEAR)));
		urlParams.put(TravelFlightStatsClient.URLParams.month.toString(), Integer.toString(calendar.get(Calendar.MONTH) + 1));
		urlParams.put(TravelFlightStatsClient.URLParams.day.toString(), Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
		return urlParams;
	}
	
	private ResponseEntity<Flights> getFlightsResponseEntity(){
		Flights flights = new Flights(Collections.singleton(new Flight()));
		return new ResponseEntity<Flights>(flights, HttpStatus.OK);
	}
	
	private TravelAPIClient getTravelAPIClientNoMocking () {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> apiKeys = new HashMap<String,String>();
		apiKeys.put("appId", "af793e4a");
		apiKeys.put("appKey", "5b7a938be9d8b167825598abcb4b724d");
		apiKeys.put("extendedOptions", "useHTTPErrors");
		return new TravelFlightStatsClient(restTemplate, apiKeys);
	}
	
	private TripInformationRequest getTripInformation() {
		return new TripInformationRequest(MEX_AIRPORT_CODE, HMO_AIRPORT_CODE, new Date());
	}
}
