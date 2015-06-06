package com.nearsoft.flights.persistence.dao;

import java.sql.Connection;

import com.nearsoft.flights.domain.model.flight.Airline;



public interface AirlineDao {
	void safeInsert(Connection conn, Airline airline);
	void deleteAll(Connection conn) ;
	Airline findByAirlineCode(Connection conn, String airlineCode);
	
	
}
