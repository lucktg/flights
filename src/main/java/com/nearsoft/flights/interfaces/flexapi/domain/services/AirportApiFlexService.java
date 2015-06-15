package com.nearsoft.flights.interfaces.flexapi.domain.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.AirportApiService;


@Service
public class AirportApiFlexService implements AirportApiService {
	private static Logger logger = Logger.getLogger(AirportApiFlexService.class);
	
	enum URLParams{
		code
	}
	
	@Value("#{apiConfig}")
	private Map<String, String> apiConfig;
	
	@Autowired
	private RestServiceInvoker restServiceInvoker;

	@Override
	public Set<Airport> getAllActiveAirports() {
		return restServiceInvoker.invoke(UriUtils.buildAllActiveAirportsJSONURI(apiConfig),
				ExtractorJsonUtils::airportJsonSetToAirportSet,
				AirportJsonSetWrapper.class);
	}

	@Override
	public Airport getAirportByCode(String airportCode) {
		logger.debug("Getting airport by airportCode ["+airportCode+"] from FlexApiService");
		Map<String,String> urlParams = new HashMap<>();
		urlParams.put(URLParams.code.name(), airportCode);
		return restServiceInvoker.invoke(UriUtils.buildAirportsJSONURI(apiConfig, urlParams), 
				ExtractorJsonUtils::airportPojoToAirport,
				AirportJsonWrapper.class);
	}

}
