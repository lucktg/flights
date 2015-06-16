package com.nearsoft.flights.domain.services;

import static com.nearsoft.flights.util.Utils.isEmptyString;
import static com.nearsoft.flights.util.Utils.isNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.nearsoft.flights.domain.model.Airport;
import com.nearsoft.flights.domain.repository.AirportRepository;
import com.nearsoft.flights.interfaces.AirportApiService;
@Service
@Transactional
public class AirportsServiceImpl implements AirportsService {
	
	private static final Logger logger = Logger.getLogger(AirportsServiceImpl.class);
	
	@Autowired
	private AirportRepository airportRepository;
	@Autowired
	private AirportApiService airportApiService;

	@Cacheable( cacheName="airports")
	@Override
	public Set<Airport> getActiveAirports() {
		logger.debug("Airports not found in cache");
		List<Airport> airports = airportRepository.getAll();
		Set<Airport> airportsSet = Collections.emptySet();
		if(airports == null || airports.isEmpty()) {
			logger.debug("Airports not found in repository, getting info from AirportApiService");
			airportsSet = airportApiService.getAllActiveAirports();
			airportRepository.addAll(airportsSet);
		} else {
			airportsSet = airports.stream().collect(Collectors.toSet());
		}
		return airportsSet;
	}

	@Cacheable( cacheName="airports")
	@Override
	public Airport getAirportByCode(String airportCode) {
		Airport airport = airportRepository.getByAirportCode(airportCode);
		if(isNull(airport )|| isEmptyString(airport.getAirportCode())) {
			logger.debug("Airports not found in repository, getting info from airportApiService");
			airport = airportApiService.getAirportByCode(airportCode);
			airportRepository.add(airport);
		}
		return airport;
	}

}
