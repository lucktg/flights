package com.nearsoft.flights.domain.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.nearsoft.flights.domain.model.Airline;
import com.nearsoft.flights.domain.repository.AirlineRepository;
import com.nearsoft.flights.domain.repository.jdbc.specification.AirlineSpecificationByCode;

@Repository("airlineRepository")
public class JdbcAirlineRepository extends JdbcRepository<Airline> implements AirlineRepository {

	public JdbcAirlineRepository() {
		super(Airline.class);
	}

	@Override
	public void fillInsertPreparedStatement(PreparedStatement ps, Airline t)
			throws SQLException {
		ps.setString(1, t.getAirlineCode());
		ps.setString(2, t.getAirlineName());
		ps.setString(3, t.getPhoneNumber());
		
	}

	@Override
	public void fillUpdatePreparedStatement(PreparedStatement ps, Airline t)
			throws SQLException {
		ps.setString(1, t.getAirlineName());
		ps.setString(2, t.getPhoneNumber());
		ps.setString(3, t.getAirlineCode());
	}

	@Override
	public void fillDeletePreparedStatement(PreparedStatement ps, Airline t)
			throws SQLException {
		ps.setString(1, t.getAirlineCode());
	}

	@Override
	public Airline fillModelObject(ResultSet rs, int rowNum)
			throws SQLException {
		return new Airline(rs.getString("airline_code"),
				rs.getString("phone_number"),
				rs.getString("airline_name"), null);
	}

	@Override
	public Airline getByAirlineCode(String airlineCode) {
		return getBySpecification(new AirlineSpecificationByCode(airlineCode));
	}
}
