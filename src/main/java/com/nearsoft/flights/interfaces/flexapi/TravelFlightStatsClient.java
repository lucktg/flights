package com.nearsoft.flights.interfaces.flexapi;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jersey.repackaged.com.google.common.collect.Lists;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.nearsoft.flights.interfaces.TravelAPIClient;
import com.nearsoft.flights.rest.util.UriUtils;
import com.nearsoft.flights.vo.Airports;
import com.nearsoft.flights.vo.FlightItemsSet;
import com.nearsoft.flights.vo.Flights;
import com.nearsoft.flights.vo.TripInformation;

public class TravelFlightStatsClient implements TravelAPIClient {

	enum URLParams {
		departureAirportCode,
		arrivalAirportCode,
		year,
		month,
		day
	}
	
	private RestTemplate restTemplate;
	
	private Map<String,String> appKeys;
	
	public TravelFlightStatsClient(RestTemplate restTemplate, Map<String,String> appKeys) {
		this.restTemplate = restTemplate;
		this.appKeys = appKeys;
	}
	
	@Override
	public Airports getAllActiveAirports() {
		return exchange(UriUtils.buildAllActiveAirportsJSONURI(appKeys), Airports.class);
	}
	
	@Override
	public Flights getDepartingFlightsByRouteAndDate(TripInformation tripInformation) {
		Map<String, String> urlParams = prepareURLParamsForFlightsByRouteAndDate(
				tripInformation.getDepartureAirportCode(),
				tripInformation.getArrivalAirportCode(),
				tripInformation.getDepartureDate());
		return exchange(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(appKeys, urlParams), Flights.class);
	}
	
	private  <E extends FlightItemsSet<?>> E exchange(URI uri, Class<E> e){
		ResponseEntity<E> flights = restTemplate.exchange(uri, HttpMethod.GET, getJSONRequest(e), e);
		return flights != null ? flights.getBody() : null;
	}

	
	private <E> HttpEntity<E> getJSONRequest(E e){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		HttpEntity<E> request = new HttpEntity<E>(null, headers);
		return request;
	}

	private Map<String, String> prepareURLParamsForFlightsByRouteAndDate(String departureAirportCode, String arrivalAirportCode, Date departureDate){
		Map<String,String> urlParams = new HashMap<String,String>();
		urlParams.put(URLParams.departureAirportCode.toString(), departureAirportCode);
		urlParams.put(URLParams.arrivalAirportCode.toString(), arrivalAirportCode);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(departureDate);
		urlParams.put(URLParams.year.toString(), Integer.toString(calendar.get(Calendar.YEAR)));
		urlParams.put(URLParams.month.toString(), Integer.toString(calendar.get(Calendar.MONTH)+1));
		urlParams.put(URLParams.day.toString(), Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
		return urlParams;
	}
	
	
}
