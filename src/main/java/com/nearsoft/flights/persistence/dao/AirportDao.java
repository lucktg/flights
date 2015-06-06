package com.nearsoft.flights.persistence.dao;

import java.sql.Connection;
import java.util.Set;

import com.nearsoft.flights.domain.model.airport.Airport;

public interface AirportDao {	
	void safeInsert(Connection conn, Set<Airport> airports);
	void deleteAll(Connection conn);
}
