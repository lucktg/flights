package com.nearsoft.flights.domain.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.repository.Repository;
import com.nearsoft.flights.domain.model.repository.jdbc.specification.AirportSpecificationByCode;
import com.nearsoft.flights.interfaces.AirportApiService;

import static com.nearsoft.flights.util.Utils.*;
@Service
public class AirportsServiceImpl implements AirportsService {
	
	private static final Logger logger = Logger.getLogger(AirportsServiceImpl.class);
	
	@Autowired
	private Repository<Airport> airportRepository;
	@Autowired
	private AirportApiService airportApiService;

	@Cacheable( cacheName="airports")
	@Override
	public Set<Airport> getActiveAirports() {
		List<Airport> airports = airportRepository.getAll();
		Set<Airport> airportsSet = Collections.emptySet();
		if(airports == null || airports.isEmpty()) {
			logger.debug("Airports not found in database, getting info from airportApiService");
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
		Airport airport = airportRepository.getBySpecification(new AirportSpecificationByCode(airportCode));
		if(isNull(airport )|| isEmptyString(airport.getAirportCode())) {
			logger.debug("Airports not found in database, getting info from airportApiService");
			airport = airportApiService.getAirportByCode(airportCode);
			airportRepository.add(airport);
		}
		return airport;
	}

}
