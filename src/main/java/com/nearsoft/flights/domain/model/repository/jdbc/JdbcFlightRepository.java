package com.nearsoft.flights.domain.model.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.domain.model.flight.Airline;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.Flight.FlightBuilder;
import com.nearsoft.flights.domain.model.flight.ScheduledTrip;

@Repository("flightRepository")
public class JdbcFlightRepository extends JdbcRepository<Flight> {
	
	public JdbcFlightRepository() {
		super(Flight.class);
	}
	
	@Override
	public void fillInsertPreparedStatement(PreparedStatement ps, Flight t)
			throws SQLException {
		ps.setString(1, t.getAirline().getAirlineCode());
		ps.setString(2, t.getArrival().getAirport().getAirportCode());
		ps.setTimestamp(3, new Timestamp(t.getArrival().getScheduledDate().getTime()));
		ps.setString(4, t.getArrival().getTerminal());
		ps.setString(5, t.getDeparture().getAirport().getAirportCode());
		ps.setTimestamp(6, new Timestamp(t.getDeparture().getScheduledDate().getTime()));
		ps.setString(7, t.getDeparture().getTerminal());
		ps.setString(8, t.getFlightNumber());
		ps.setString(9, t.getServiceType());
	}

	@Override
	public void fillUpdatePreparedStatement(PreparedStatement ps, Flight t)
			throws SQLException {
		ps.setTimestamp(1, new Timestamp(t.getArrival().getScheduledDate().getTime()));
		ps.setString(2, t.getArrival().getTerminal());
		ps.setTimestamp(3, new Timestamp(t.getDeparture().getScheduledDate().getTime()));
		ps.setString(4, t.getDeparture().getTerminal());
		ps.setString(5, t.getServiceType());
		ps.setString(6, t.getAirline().getAirlineCode());
		ps.setString(7, t.getFlightNumber());
		
	}

	@Override
	public void fillDeletePreparedStatement(PreparedStatement ps, Flight t)
			throws SQLException {
		ps.setString(1, t.getAirline().getAirlineCode());
		ps.setString(2, t.getFlightNumber());
	}

	@Override
	public Flight fillModelObject(ResultSet rs, int rowNum) throws SQLException {
		Airline airline = new Airline(rs.getString("airline_code"), null, null, null);
		AirportBuilder arrivalBuilder = new AirportBuilder(rs.getString("arrival_airport_code"));
		AirportBuilder departureBuilder = new AirportBuilder(rs.getString("departure_airport_code"));
		FlightBuilder builder = new FlightBuilder(rs.getString("flight_number"), airline);
		builder.addFlightRoute(new ScheduledTrip(departureBuilder.build(), rs.getDate("departure_date"), rs.getString("departure_terminal")),
				new ScheduledTrip(arrivalBuilder.build(), rs.getDate("arrival_date"),rs.getString("arrival_terminal")));
		builder.addServiceType(rs.getString("service_type"));
		return builder.build();
	}

}
