package com.nearsoft.flights.persistence.dao;

import java.sql.SQLException;
import java.util.Set;

import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;
import com.nearsoft.flights.persistence.dto.AirlineDto;

public interface AirlineDao {
	Set<AirlineDto> findAll() throws SQLException;
	AirlineDto findByAirlineCode(String airlineCode) throws PersistenceException;
	void insert(Set<AirlineDto> airlines) throws PersistenceException;
	void insert(AirlineDto airline) throws PersistenceException;
	void deleteAll() throws PersistenceException;
	
	
}
