package com.nearsoft.flights.service;

import java.util.Set;

import com.nearsoft.flights.vo.Airport;

public interface AirportsService {
	Set<Airport> getActiveAirports();
	Airport getAirportByCode(String airportCode);
}
