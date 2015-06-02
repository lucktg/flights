package com.nearsoft.flights.persistence.dao;

import java.util.Set;

import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;
import com.nearsoft.flights.persistence.dto.AirportDto;

public interface AirportDao {
	
	void insert(AirportDto airport) throws PersistenceException;
	void insert(Set<AirportDto> airports) throws PersistenceException;
	Set<AirportDto> findAll() throws PersistenceException;	
	AirportDto findByAirportCode(String airportCode) throws PersistenceException;
	void deleteAll() throws PersistenceException;
}
