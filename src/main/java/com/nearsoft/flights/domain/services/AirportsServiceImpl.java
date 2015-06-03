package com.nearsoft.flights.domain.services;

import java.util.Set;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.AirportRepository;
import com.nearsoft.flights.domain.model.exception.RepositoryException;
import com.nearsoft.flights.domain.services.exception.ServiceException;
public class AirportsServiceImpl implements AirportsService {
	
	private AirportRepository airportRepository;
	
	public AirportsServiceImpl(AirportRepository airportRepository) {
		this.airportRepository = airportRepository;
	}

	@Override
	public Set<Airport> getActiveAirports() throws ServiceException {
		try {
			return airportRepository.findAllActiveAirports();
		} catch (RepositoryException e) {
			throw new ServiceException("Error occurred while gathering airports information", e);
		}
	}

	@Override
	public Airport getAirportByCode(String airportCode) throws ServiceException {
		try {
			return airportRepository.findByAirportCode(airportCode);
		} catch (RepositoryException e) {
			throw new ServiceException("Error occurred while gathering airport information", e);
		}
	}

}
