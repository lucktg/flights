package com.nearsoft.flights.dao;

import java.util.Set;

import com.nearsoft.flights.vo.Airport;
public interface AirportDAO {
	
	Set<Airport> findActiveAirports();

	void updateAirports(Set<Airport> travelItem);

}
