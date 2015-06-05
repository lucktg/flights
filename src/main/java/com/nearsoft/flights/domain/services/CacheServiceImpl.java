package com.nearsoft.flights.domain.services;

import com.nearsoft.flights.domain.model.airport.AirportRepository;
import com.nearsoft.flights.domain.model.flight.FlightService;
import com.nearsoft.flights.rest.resources.CacheResource.CacheType;

public class CacheServiceImpl implements CacheService {

	private AirportRepository airportRepository;
	
	private FlightService flightRepository;
	
	private CatalogsRepository catalogsRepository;
	
	public CacheServiceImpl(AirportRepository airportRepository,
			FlightService flightRepository) {
		this.airportRepository = airportRepository;
		this.flightRepository = flightRepository;
	}



	@Override
	public void clean(CacheType type) {
		switch(type) {
		case airports :
			airportRepository.removeAll();
			break;
		case flights : 
			catalogsRepository.removeAll();
			flightRepository.removeAll();
			break;
		default : 
			airportRepository.removeAll();
			catalogsRepository.removeAll();
			flightRepository.removeAll();
		}
	}

}
