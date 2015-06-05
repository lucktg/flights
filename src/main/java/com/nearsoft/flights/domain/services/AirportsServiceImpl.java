package com.nearsoft.flights.domain.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.AirportRepository;
import com.nearsoft.flights.interfaces.flexapi.AirportFlexApiService;

@Service
public class AirportsServiceImpl implements AirportsService {
	
	@Autowired
	private AirportRepository airportRepository;
	@Autowired
	private AirportFlexApiService airportFlexApiService;

	@Cacheable( cacheName="airports")
	@Override
	public Set<Airport> getActiveAirports() {
		Set<Airport> airports = null;
		if((airports =airportFlexApiService.getAllActiveAirports())== null || airports.isEmpty()) {
			airportRepository.add(airports = airportFlexApiService.getAllActiveAirports());
		}
		return airports;
	}

	@Cacheable( cacheName="airports")
	@Override
	public Airport getAirportByCode(String airportCode) {
		Airport airport = null;
		if((airport =airportFlexApiService.getAirportByCode(airportCode))== null) {
			airportRepository.add(airport = airportFlexApiService.getAirportByCode(airportCode));
		}
		return airport;
	}

}
