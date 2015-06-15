package com.nearsoft.flights.domain.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.TriggersRemove;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.flight.Airline;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.repository.Repository;

@Service
public class CacheServiceImpl implements CacheService {
	
	private static final Logger logger = Logger.getLogger(CacheServiceImpl.class);

	@Autowired
	private Repository<Airport> airportRepository;
	
	@Autowired
	private Repository<Flight> flightRepository;
	
	@Autowired
	private Repository<Airline> airlineRepository;

	@TriggersRemove(cacheName={"airports","flights"}, removeAll=true)
	@Scheduled(initialDelay=60000, fixedDelay=60000)
	@Override
	public void clean() {
		logger.debug("Cleaning cache");
		flightRepository.removeAll();
		airportRepository.removeAll();
		airlineRepository.removeAll();
	}

}
