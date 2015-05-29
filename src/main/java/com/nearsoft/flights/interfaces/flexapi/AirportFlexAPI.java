package com.nearsoft.flights.interfaces.flexapi;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import jersey.repackaged.com.google.common.collect.Lists;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.AirportAPI;
import com.nearsoft.flights.rest.util.UriUtils;

public class AirportFlexAPI implements AirportAPI {

	private RestTemplate restTemplate;
	
	private Map<String, ?> apiConfig;
	
	public AirportFlexAPI(RestTemplate restTemplate, Map<String, ?> apiConfig) {
		this.restTemplate = restTemplate;
		this.apiConfig = apiConfig;
	}
	
	@Override
	public Set<Airport> getAllActiveAirports() {
		return restTemplate.execute(UriUtils.buildAllActiveAirportsJSONURI(apiConfig), 
				HttpMethod.GET, 
				new EmptyRequestCallback(Collections.singletonList(MediaType.APPLICATION_JSON)), 
				new GenericResponseExtractor<Set<Airport>>(new AirportSetExtractorFactory()));
	}

	@Override
	public Airport getAirportByCode(String airportCode) {
		return restTemplate.execute(UriUtils.buildAllActiveAirportsJSONURI(apiConfig), 
				HttpMethod.GET, 
				new EmptyRequestCallback(Collections.singletonList(MediaType.APPLICATION_JSON)), 
				new GenericResponseExtractor<Airport>(new AirportExtractorFactory()));
	}

}
