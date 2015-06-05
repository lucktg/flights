package com.nearsoft.flights.persistence.dao;

import java.util.Set;

import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;
import com.nearsoft.flights.persistence.dto.Airport;

@Deprecated
public interface AirportDao {
	
	void insert(Airport airport) throws PersistenceException;
	void insert(Set<Airport> airports) throws PersistenceException;
	Set<Airport> findAll() throws PersistenceException;	
	Airport findByAirportCode(String airportCode) throws PersistenceException;
	void deleteAll() throws PersistenceException;
}
