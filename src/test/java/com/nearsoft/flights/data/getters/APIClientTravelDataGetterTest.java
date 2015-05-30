package com.nearsoft.flights.data.getters;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

import com.nearsoft.flights.interfaces.TravelAPIClient;
import com.nearsoft.flights.persistence.dao.AirportDAO;
import com.nearsoft.flights.persistence.dao.FlightDAO;
import com.nearsoft.flights.vo.AirportDTO;
import com.nearsoft.flights.vo.Airports;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.Flights;
import com.nearsoft.flights.vo.TripInformationRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class APIClientTravelDataGetterTest {
		
	private static final String HMO_AIRPORT_CODE = "HMO";

	private static final String MEX_AIRPORT_CODE = "MEX";

	private APIClientTravelDataGetter apiClientTravelDataGetter;
	
	@Mock
	private TravelAPIClient travelAPIClient;
	
	@Mock
	private AirportDAO airportDAO;
	
	@Mock
	private FlightDAO flightDAO;
	
	@Mock
	DatabaseTravelDataGetter dbGetter;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		List<Observer> observers = new ArrayList<Observer>();
		observers.add(dbGetter);
		apiClientTravelDataGetter = new APIClientTravelDataGetter(travelAPIClient,observers);
	}
	
	@Test
	public void shouldReturnEmptyAirportSet() {
		when(travelAPIClient.getAllActiveAirports()).thenReturn(null);

		Set<AirportDTO> airports = apiClientTravelDataGetter.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(empty()));
		verify(dbGetter).update(apiClientTravelDataGetter, null);
		verify(travelAPIClient).getAllActiveAirports();
	}
	
	@Test
	public void shouldReturnNotEmptyAirportSet() {
		Airports airports = getAirports();
		when(travelAPIClient.getAllActiveAirports()).thenReturn(airports);

		Set<AirportDTO> airportSet = apiClientTravelDataGetter.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airportSet, is(not(empty())));
		
		verify(travelAPIClient).getAllActiveAirports();
		verify(dbGetter).update(apiClientTravelDataGetter, airports);
	}
	
	@Test
	public void shouldReturnEmptyAirportSetWhenAirportSetIsNull() {
		Airports nullAirportSet = getAirportsNullAirportsSet();
		when(travelAPIClient.getAllActiveAirports()).thenReturn(nullAirportSet);
		Set<AirportDTO> airports = apiClientTravelDataGetter.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(empty()));
		
		verify(travelAPIClient).getAllActiveAirports();
		verify(dbGetter).update(apiClientTravelDataGetter, nullAirportSet);
	}
	
	@Test
	public void shouldReturnEmptyFlightsSet() {
		TripInformationRequest tripInformation = getTripInformation();
		when(travelAPIClient.getDepartingFlightsByRouteAndDate(tripInformation)).thenReturn(null);

		Set<Flight> flights = apiClientTravelDataGetter.getDepartingFlightsByRouteNDate(tripInformation);
		Assert.assertNotNull(flights);
		Assert.assertThat(flights, is(empty()));
		
		verify(travelAPIClient).getDepartingFlightsByRouteAndDate(tripInformation);
		verify(dbGetter).update(apiClientTravelDataGetter, null);
	}
	
	@Test
	public void shouldReturnNotEmptyFlightSet() {
		TripInformationRequest tripInformation = getTripInformation();
		Flights flights = getFlights();
		when(travelAPIClient.getDepartingFlightsByRouteAndDate(tripInformation)).thenReturn(flights);

		Set<Flight> flightsSet = apiClientTravelDataGetter.getDepartingFlightsByRouteNDate(tripInformation);
		Assert.assertNotNull(flightsSet);
		Assert.assertThat(flightsSet, is(not(empty())));
		
		verify(travelAPIClient).getDepartingFlightsByRouteAndDate(tripInformation);
		verify(dbGetter).update(apiClientTravelDataGetter, flights);
	}
	
	

	@Test
	public void shouldReturnEmptyFlightSetWhenAirportSetIsNull() {
		TripInformationRequest tripInformation = getTripInformation();
		Flights nullFlightSet = getFlightsNullFlightSet();
		when(travelAPIClient.getDepartingFlightsByRouteAndDate(tripInformation)).thenReturn(nullFlightSet);
		Set<Flight> flights = apiClientTravelDataGetter.getDepartingFlightsByRouteNDate(tripInformation);
		Assert.assertNotNull(flights);
		Assert.assertThat(flights, is(empty()));
		
		verify(travelAPIClient).getDepartingFlightsByRouteAndDate(tripInformation);
		verify(dbGetter).update(apiClientTravelDataGetter, nullFlightSet);
	}
	

	private Airports getAirports() {
		Set<AirportDTO> airportsSet = Collections.singleton(new AirportDTO());
		Airports airports = new Airports(airportsSet);
		return airports;
	}
	
	private Airports getAirportsNullAirportsSet() {
		return new Airports(null);
	}
	
	private Flights getFlights() {
		Set<Flight> flightSet = Collections.singleton(new Flight());
		Flights flights = new Flights(flightSet);
		return flights;
	}
	
	private Flights getFlightsNullFlightSet() {
		return new Flights(null);
	}
	
	private TripInformationRequest getTripInformation() {
		return new TripInformationRequest(MEX_AIRPORT_CODE, HMO_AIRPORT_CODE, new Date());
	}
}
