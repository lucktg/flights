package com.nearsoft.flights.domain.services;

import java.util.Set;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.AirportRepository;
import com.nearsoft.flights.interfaces.AirportApiService;

@Service
public class AirportsServiceImpl implements AirportsService {
	
	private static final Logger logger = Logger.getLogger(AirportsServiceImpl.class);
	
	@Autowired
	private AirportRepository airportRepository;
	@Autowired
	private AirportApiService airportApiService;

	@Cacheable( cacheName="airports")
	@Override
	public Set<Airport> getActiveAirports() {
		Set<Airport> airports = null;
		if((airports =airportApiService.getAllActiveAirports())== null || airports.isEmpty()) {
			logger.debug("Airports not found in database, getting info from airportApiService");
			airportRepository.add(airports = airportApiService.getAllActiveAirports());
		}
		return airports;
	}

	@Cacheable( cacheName="airports")
	@Override
	public Airport getAirportByCode(String airportCode) {
		Airport airport = null;
		if((airport =airportApiService.getAirportByCode(airportCode))== null) {
			logger.debug("Airports not found in database, getting info from airportApiService");
			airportRepository.add(airport = airportApiService.getAirportByCode(airportCode));
		}
		return airport;
	}

}
