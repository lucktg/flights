package com.nearsoft.flights.domain.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.nearsoft.flights.domain.model.Airport;
import com.nearsoft.flights.domain.model.Airport.AirportBuilder;
import com.nearsoft.flights.domain.repository.AirportRepository;
import com.nearsoft.flights.domain.repository.jdbc.specification.AirportSpecificationByCode;

@Repository("airportRepository")
public class JdbcAirportRepository  extends JdbcRepository<Airport> implements AirportRepository {
	
	public JdbcAirportRepository() {
		super(Airport.class);
	}
	
	@Override
	public void fillInsertPreparedStatement(PreparedStatement ps, Airport t) throws SQLException {
		ps.setString(1, t.getAirportCode());
		ps.setString(2, t.getAirportName());
		ps.setString(3, t.getCity());
		ps.setString(4, t.getCityCode());
		ps.setString(5, t.getCountryCode());
		ps.setString(6, t.getCountryName());
		ps.setString(7, t.getLatitude());
		ps.setString(8, t.getLongitude());
		
		//ps.setString(9, t.getStateCode());
	}

	@Override
	public void fillUpdatePreparedStatement(PreparedStatement ps, Airport t) throws SQLException{
		ps.setString(1, t.getCity());
		ps.setString(2, t.getCityCode());
		ps.setString(3, t.getCountryCode());
		ps.setString(4, t.getCountryName());
		ps.setString(5, t.getLatitude());
		ps.setString(6, t.getLongitude());
		ps.setString(7, t.getAirportName());
		ps.setString(8, t.getAirportCode());
	}

	@Override
	public void fillDeletePreparedStatement(PreparedStatement ps, Airport t) throws SQLException{
		ps.setString(1, t.getAirportCode());
	}

	@Override
	public Airport fillModelObject(ResultSet rs, int rowNum) throws SQLException {
		AirportBuilder builder = new AirportBuilder(rs.getString("airport_code"));
		builder.addAirportName(rs.getString("airport_name"));
		builder.addCity(rs.getString("city"));
		builder.addCityCode(rs.getString("city_code"));
		builder.addCountryCode(rs.getString("country_code"));
		builder.addCountryName(rs.getString("country_name"));
		builder.addLatitude(rs.getString("latitude"));
		builder.addLongitude(rs.getString("longitude"));
		return builder.build();
	}

	@Override
	public Airport getByAirportCode(String airportCode) {
		return getBySpecification(new AirportSpecificationByCode(airportCode));
	}

}
