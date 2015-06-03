package com.nearsoft.flights.domain.services;

import java.util.Set;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.services.exception.ServiceException;

public interface AirportsService {
	Set<Airport> getActiveAirports() throws ServiceException;
	Airport getAirportByCode(String airportCode) throws ServiceException;
}
