package com.nearsoft.flights.domain.model.flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.domain.model.exception.RepositoryException;
import com.nearsoft.flights.domain.model.flight.Flight.FlightBuilder;
import com.nearsoft.flights.persistence.dao.AirlineDao;
import com.nearsoft.flights.persistence.dao.AirportDao;
import com.nearsoft.flights.persistence.dao.jdbc.utils.JdbcUtils;

@Repository
public class JdbcFlightRepository implements FlightRepository {
	
	private static final Logger logger = Logger.getLogger(JdbcFlightRepository.class);
	
	@Autowired
	private AirportDao airportDao;
	@Autowired
	private AirlineDao airlineDao;

	private static final String INSERT = "INSERT INTO FLIGHT (FLIGHT_NUMBER, AIRLINE_CODE, DEPARTURE_DATE, DEPARTURE_TERMINAL, DEPARTURE_AIRPORT_CODE, ARRIVAL_DATE, ARRIVAL_TERMINAL, ARRIVAL_AIRPORT_CODE, SERVICE_TYPE) VALUES(?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_BY_TRIP_INFORMATION = "SELECT A.FLIGHT_NUMBER, A.DEPARTURE_DATE, A.DEPARTURE_TERMINAL, A.ARRIVAL_DATE, A.ARRIVAL_TERMINAL, A.SERVICE_TYPE, B.AIRLINE_CODE, B.AIRLINE_NAME, B.PHONE_NUMBER, C.AIRPORT_CODE,C.AIRPORT_NAME,C.CITY,C.CITY_CODE,C.COUNTRY_CODE,C.COUNTRY_NAME,C.LATITUDE,C.LONGITUDE, D.AIRPORT_CODE,D.AIRPORT_NAME,D.CITY,D.CITY_CODE,D.COUNTRY_CODE,D.COUNTRY_NAME,D.LATITUDE,D.LONGITUDE "
			+ "FROM FLIGHT A, AIRLINE B, AIRPORT C, AIRPORT D "
			+ "WHERE A.AIRLINE_CODE =B.AIRLINE_CODE AND A.DEPARTURE_AIRPORT_CODE = C.AIRPORT_CODE AND A.ARRIVAL_AIRPORT_CODE = D.AIRPORT_CODE AND A.DEPARTURE_AIRPORT_CODE = ? AND A.ARRIVAL_AIRPORT_CODE = ? AND A.DEPARTURE_DATE between  ? and ?";
	private static final String DELETE = "DELETE FROM FLIGHT";
	
	@Autowired
	private DataSource datasource;

	@Override
	public void add(Set<Flight> flights) {
		logger.debug("Adding flights to repository ["+flights+"]");
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(INSERT);
			Iterator<Flight> it = flights !=null ? flights.iterator() : null;
			Flight flight = null;
			while(it != null && it.hasNext()) {
				flight = it.next();
				Set<Airport> airports = new HashSet<>();
				airports.add(flight.getArrival().getAirport());
				airports.add(flight.getDeparture().getAirport());
				airportDao.safeInsert(conn, airports);
				airlineDao.safeInsert(conn, flight.getAirline());
				st.setString(1, flight.getFlightNumber());
				st.setString(2,  flight.getAirline().getAirlineCode());
				st.setTimestamp(3, new Timestamp(flight.getDeparture().getScheduledDate().getTime()));
				st.setString(4, flight.getDeparture().getTerminal());
				st.setString(5, flight.getDeparture().getAirport().getAirportCode());
				st.setTimestamp(6, new Timestamp(flight.getArrival().getScheduledDate().getTime()));
				st.setString(7, flight.getArrival().getTerminal());
				st.setString(8, flight.getArrival().getAirport().getAirportCode());
				st.setString(9, flight.getServiceType());
				st.addBatch();
			}
			st.executeBatch();
			conn.commit();
		} catch(SQLException ex) {
			logger.debug(ex);
			if(conn != null)
				try {
					conn.rollback();
				} catch (SQLException e) {
					throw new RepositoryException("Error occured while rolling back flight data", ex);
				}
			throw new RepositoryException("Error occured while inserting flight data", ex);
		} finally {
			JdbcUtils.close(conn, st, rs);
		}
	}

	@Override
	public Set<Flight> findDepartingFlightsByTripInformationRequest(
			TripInformationRequest tripInformationRequest) {
		logger.debug("searching flights by trip information ["+tripInformationRequest+"]");
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(SELECT_BY_TRIP_INFORMATION);
			st.setString(1, tripInformationRequest.getDepartureAirportCode());
			st.setString(2, tripInformationRequest.getArrivalAirportCode());
			st.setTimestamp(3, new Timestamp(tripInformationRequest.getDepartureDate().getTime()));
			st.setTimestamp(4, new Timestamp(tripInformationRequest.getDepartureDateEndDay().getTime()));
			rs  = st.executeQuery();
			Set<Flight> flights = new HashSet<>();
			FlightBuilder builder = null;
			while(rs != null && rs.next()) {
				builder = new FlightBuilder(rs.getString(1), new Airline(rs.getString(7), rs.getString(9), rs.getString(8), null));				
				builder.addDeparture(new ScheduledTrip(getAirport(rs, 10), rs.getTimestamp(2), rs.getString(3)));
				builder.addArrival(new ScheduledTrip(getAirport(rs,18), rs.getTimestamp(4), rs.getString(5)));
				builder.addServiceType(rs.getString(6));
				flights.add(builder.build());
			}
			return flights;
		} catch(SQLException ex) {
			logger.debug(ex);
			throw new RepositoryException("Error occured while fetching flight data",ex);
		} finally {
			JdbcUtils.close(conn, st, rs);
		}
	}
	
	private Airport getAirport(ResultSet rs, int index) throws SQLException {
		AirportBuilder builder = new AirportBuilder(rs.getString(index++));
		builder.addAirportName(rs.getString(index++));
		builder.addCity(rs.getString(index++));
		builder.addCityCode(rs.getString(index++));
		builder.addCountryCode(rs.getString(index++));
		builder.addCountryName(rs.getString(index++));
		builder.addLatitude(rs.getString(index++));
		builder.addLongitude(rs.getString(index++));
		return builder.build();
	}

	@Override
	public void removeAll() {
		logger.debug("Deleting all flights and dependencies");
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(DELETE);
			st.executeUpdate();
			airportDao.deleteAll(conn);
			airlineDao.deleteAll(conn);
		} catch (SQLException ex) {
			logger.debug(ex);
			throw new RepositoryException("Error ocurred while deleting flights data",ex);
		} finally {
			JdbcUtils.close(conn, st);
		}
	}

}
