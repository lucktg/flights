package com.nearsoft.flights.domain.services;

import com.nearsoft.flights.domain.model.airport.AirportRepository;
import com.nearsoft.flights.domain.model.flight.FlightRepository;
import com.nearsoft.flights.rest.resources.CacheResource.CacheType;

public class CacheServiceImpl implements CacheService {

	private AirportRepository airportRepository;
	
	private FlightRepository flightRepository;
	
	private CatalogsRepository catalogsRepository;
	
	public CacheServiceImpl(AirportRepository airportRepository,
			FlightRepository flightRepository) {
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
