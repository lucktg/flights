package com.nearsoft.flights.domain.model.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.poi.ss.formula.functions.T;

import com.nearsoft.flights.domain.model.airport.Airport;

public class AirportByCode implements SqlSpecification<Airport> {
	
	public 

	@Override
	public PreparedStatement fillPreparedStatement(Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T fillModelObject(ResultSet rs, int rowNum) {
		rs.get
		// TODO Auto-generated method stub
		return null;
	}

}
