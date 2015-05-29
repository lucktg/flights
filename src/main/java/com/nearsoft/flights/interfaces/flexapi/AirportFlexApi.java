package com.nearsoft.flights.interfaces.flexapi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.AirportApi;
import com.nearsoft.flights.interfaces.flexapi.extractor.MediaTypeResponseExtractor;
import com.nearsoft.flights.interfaces.flexapi.extractor.json.AirportExtractorFactory;
import com.nearsoft.flights.interfaces.flexapi.extractor.json.AirportSetExtractorFactory;
import com.nearsoft.flights.rest.util.UriUtils;

public class AirportFlexApi implements AirportApi {
	enum URLParams{
		code
	}
	private RestTemplate restTemplate;
	
	private Map<String, String> apiConfig;
	
	public AirportFlexApi(RestTemplate restTemplate, Map<String, String> apiConfig) {
		this.restTemplate = restTemplate;
		this.apiConfig = apiConfig;
	}
	
	@Override
	public Set<Airport> getAllActiveAirports() {
		return restTemplate.execute(UriUtils.buildAllActiveAirportsJSONURI(apiConfig), 
				HttpMethod.GET, 
				new EmptyRequestCallback(Collections.singletonList(MediaType.APPLICATION_JSON)), 
				new MediaTypeResponseExtractor<Set<Airport>>(new AirportSetExtractorFactory()));
	}

	@Override
	public Airport getAirportByCode(String airportCode) {
		Map<String,String> urlParams = new HashMap<>();
		urlParams.put(URLParams.code.name(), airportCode);
		return restTemplate.execute(UriUtils.buildAirportsJSONURI(apiConfig, urlParams), 
				HttpMethod.GET, 
				new EmptyRequestCallback(Collections.singletonList(MediaType.APPLICATION_JSON)), 
				new MediaTypeResponseExtractor<Airport>(new AirportExtractorFactory()));
	}

}
