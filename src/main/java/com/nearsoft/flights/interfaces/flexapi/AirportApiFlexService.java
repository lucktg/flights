package com.nearsoft.flights.interfaces.flexapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class AirportApiFlexService implements AirportApiService {
	private static Logger logger = Logger.getLogger(AirportApiFlexService.class);
	
	enum URLParams{
		code
	}
	
	private RestTemplate restTemplate = new RestTemplate();
	@Autowired
	private RequestCallback requestCallback;
	@Value("#{apiConfig}")
	private Map<String, String> apiConfig;
	
	
	@Override
	public Set<Airport> getAllActiveAirports() {
		logger.debug("Getting all airports from FlexApiService");
		return restTemplate.execute(UriUtils.buildAllActiveAirportsJSONURI(apiConfig), 
				HttpMethod.GET, 
				requestCallback, 
				new MediaTypeResponseExtractor<Set<Airport>>(AirportSetExtractorFactory.getInstance()));
	}

	@Override
	public Airport getAirportByCode(String airportCode) {
		logger.debug("Getting airport by airportCode ["+airportCode+"] from FlexApiService");
		Map<String,String> urlParams = new HashMap<>();
		urlParams.put(URLParams.code.name(), airportCode);
		return restTemplate.execute(UriUtils.buildAirportsJSONURI(apiConfig, urlParams), 
				HttpMethod.GET, 
				requestCallback, 
				new MediaTypeResponseExtractor<Airport>(AirportExtractorFactory.getInstance()));
	}

}
