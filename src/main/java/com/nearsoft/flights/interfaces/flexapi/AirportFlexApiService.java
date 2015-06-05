package com.nearsoft.flights.interfaces.flexapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.AirportApiService;
import com.nearsoft.flights.interfaces.flexapi.extractor.AirportExtractorFactory;
import com.nearsoft.flights.interfaces.flexapi.extractor.AirportSetExtractorFactory;
import com.nearsoft.flights.interfaces.flexapi.extractor.MediaTypeResponseExtractor;
import com.nearsoft.flights.rest.util.UriUtils;

@Service
public class AirportFlexApiService implements AirportApiService {
	enum URLParams{
		code
	}
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private RequestCallback requestCallback;
	@Autowired
	private Map<String, String> apiConfig;
	
	
	@Override
	public Set<Airport> getAllActiveAirports() {
		return restTemplate.execute(UriUtils.buildAllActiveAirportsJSONURI(apiConfig), 
				HttpMethod.GET, 
				requestCallback, 
				new MediaTypeResponseExtractor<Set<Airport>>(new AirportSetExtractorFactory()));
	}

	@Override
	public Airport getAirportByCode(String airportCode) {
		Map<String,String> urlParams = new HashMap<>();
		urlParams.put(URLParams.code.name(), airportCode);
		return restTemplate.execute(UriUtils.buildAirportsJSONURI(apiConfig, urlParams), 
				HttpMethod.GET, 
				requestCallback, 
				new MediaTypeResponseExtractor<Airport>(new AirportExtractorFactory()));
	}

}
