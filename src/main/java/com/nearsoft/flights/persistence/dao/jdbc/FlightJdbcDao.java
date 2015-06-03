package com.nearsoft.flights.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.nearsoft.flights.persistence.dao.FlightDao;
import com.nearsoft.flights.persistence.dto.AirlineDto;
import com.nearsoft.flights.persistence.dto.AirportDto;
import com.nearsoft.flights.persistence.dto.FlightDto;
import com.nearsoft.flights.persistence.dto.TripInformationRequestDto;

public class FlightJdbcDao implements FlightDao {
	
	private static final String INSERT_AIRLINE = "INSERT INTO AIRLINE (AIRLINE_CODE, AIRLINE_NAME,PHONE_NUMBER) VALUES(?,?,?)";
	private static final String SELECT_AIRLINE = "SELECT AIRLINE_CODE FROM AIRLINE WHERE AIRLINE_CODE = ?";
	private static final String INSERT_AIRPORT = "INSERT INTO AIRPORT (AIRPORT_CODE, AIRPORT_NAME, CITY, CITY_CODE, COUNTRY_CODE, COUNTRY_NAME, LATITUDE, LONGITUDE) VALUES (?,?,?,?,?,?,?,?)";
	private static final String SELECT_AIRPORT = "SELECT AIRPORT_CODE, LATITUDE, LONGITUDE FROM AIRPORT WHERE AIRPORT_CODE IN (?)";
	private static final String INSERT = "INSERT INTO FLIGHT (FLIGHT_NUMBER, AIRLINE_CODE, DEPARTURE_DATE, DEPARTURE_TERMINAL, DEPARTURE_AIRPORT_CODE, ARRIVAL_DATE, ARRIVAL_TERMINAL, ARRIVAL_AIRPORT_CODE, SERVICE_TYPE) VALUES(?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_BY_TRIP_INFORMATION = "SELECT A.FLIGHT_NUMBER, A.DEPARTURE_DATE, A.DEPARTURE_TERMINAL, A.ARRIVAL_DATE, A.ARRIVAL_TERMINAL, A.SERVICE_TYPE, B.AIRLINE_CODE, B.AIRLINE_NAME, B.PHONE_NUMBER, C.AIRPORT_CODE,C.AIRPORT_NAME,C.CITY,C.CITY_CODE,C.COUNTRY_CODE,C.COUNTRY_NAME,C.LATITUDE,C.LONGITUDE, D.AIRPORT_CODE,D.AIRPORT_NAME,D.CITY,D.CITY_CODE,D.COUNTRY_CODE,D.COUNTRY_NAME,D.LATITUDE,D.LONGITUDE "
			+ "FROM FLIGHT A, AIRLINE B, AIRPORT C, AIRPORT D "
			+ "WHERE A.AIRLINE_CODE =B.AIRLINE_CODE AND A.DEPARTURE_AIRPORT_CODE = C.AIRPORT_CODE AND A.ARRIVAL_AIRPORT_CODE = D.AIRPORT_CODE AND A.DEPARTURE_AIRPORT_CODE = ? AND A.ARRIVAL_AIRPORT_CODE = ? AND A.DEPARTURE_DATE between  ? and ?";
	private static final String DELETE = "DELETE FROM FLIGHT";
	private DataSource datasource;
	
	public FlightJdbcDao(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void insertFlights(Set<FlightDto> flights) throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(INSERT);
			Iterator<FlightDto> it = flights !=null ? flights.iterator() : null;
			FlightDto flight = null;
			while(it != null && it.hasNext()) {
				flight = it.next();
				Set<AirportDto> airports = new HashSet<>();
				airports.add(flight.getArrivalAirport());
				airports.add(flight.getDepartureAirport());
				insertAirports(conn, airports);
				insertAirline(conn, flight.getAirline());
				st.setString(1, flight.getFlightNumber());
				st.setString(2,  flight.getAirline().getAirlineCode());
				st.setTimestamp(3, flight.getDepartureDate());
				st.setString(4, flight.getDepartureTerminal());
				st.setString(5, flight.getDepartureAirport().getAirportCode());
				st.setTimestamp(6, flight.getArrivalDate());
				st.setString(7, flight.getArrivalTerminal());
				st.setString(8, flight.getArrivalAirport().getAirportCode());
				st.setString(9, flight.getServiceType());
				st.addBatch();
			}
			st.executeBatch();
			conn.commit();
		} catch(SQLException ex) {
			if(conn != null)
				try {
					conn.rollback();
				} catch (SQLException e) {
					throw new PersistenceException("Error occured while rolling back flight data",ex);
				}
			throw new PersistenceException("Error occured while inserting flight data",ex);
		} finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Set<FlightDto> findDepartingFlightsByTripInformationRequest(
			TripInformationRequestDto tripInformationRequestDto) throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(SELECT_BY_TRIP_INFORMATION);
			st.setString(1, tripInformationRequestDto.getDepartureAirportCode());
			st.setString(2, tripInformationRequestDto.getArrivalAirportCode());
			st.setTimestamp(3, tripInformationRequestDto.getDepartureDate());
			st.setTimestamp(4, tripInformationRequestDto.getDepartureDateEndDay());
			rs  = st.executeQuery();
			Set<FlightDto> flights = new HashSet<>();
			FlightDto dto = null;
			int index = 7;
			while(rs != null && rs.next()) {
				dto = new FlightDto();
				dto.setFlightNumber(rs.getString(1));
				dto.setDepartureDate(rs.getTimestamp(2));
				dto.setDepartureTerminal(rs.getString(3));
				dto.setArrivalDate(rs.getTimestamp(4));
				dto.setArrivalTerminal(rs.getString(5));
				dto.setServiceType(rs.getString(6));
				dto.setAirline(getAirlineDto(rs, 7));
				dto.setDepartureAirport(getAirport(rs, 10));
				dto.setArrivalAirport(getAirport(rs, 18));
				flights.add(dto);
			}
			return flights;
		} catch(SQLException ex) {
			throw new PersistenceException("Error occured while fetching flight data",ex);
		} finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private AirportDto getAirport(ResultSet rs, int index) throws SQLException {
		AirportDto dto = new AirportDto();
		dto.setAirportCode(rs.getString(index++));
		dto.setAirportName(rs.getString(index++));
		dto.setCity(rs.getString(index++));
		dto.setCityCode(rs.getString(index++));
		dto.setCountryCode(rs.getString(index++));
		dto.setCountryName(rs.getString(index++));
		dto.setLatitude(rs.getString(index++));
		dto.setLongitude(rs.getString(index++));
		return dto;
	}

	private AirlineDto getAirlineDto(ResultSet rs, int index) throws SQLException {
		AirlineDto dto = new AirlineDto();
		dto.setAirlineCode(rs.getString(index++));
		dto.setAirlineName(rs.getString(index++));
		dto.setPhoneNumber(rs.getString(index++));
		return dto;
	}

	@Override
	public void deleteAll() throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(DELETE);
			st.executeUpdate();
		} catch (SQLException ex) {
			throw new PersistenceException("Error ocurred while deleting flights data",ex);
		} finally {
			try {
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void insertAirports(Connection conn, Set<AirportDto> airports) throws PersistenceException {
		PreparedStatement st = null;
		PreparedStatement inSt = null;
		ResultSet rs = null;
		try {
			List<String> params = Collections.nCopies(airports.size(), "?");
			String str = params.stream().collect(Collectors.joining(","));
			st = conn.prepareStatement(SELECT_AIRPORT.replace("?", str));
			int index=1;
			for(AirportDto dto:airports) {
				st.setString(index++, dto.getAirportCode());
			}
			rs = st.executeQuery();
			while(rs != null && rs.next()) {
				AirportDto dto = new AirportDto();
				dto.setAirportCode(rs.getString(1));
				dto.setLatitude(rs.getString(2));
				dto.setLongitude(rs.getString(3));
				airports.remove(dto);
			}
			if(airports != null && airports.size() >0 ) {
				inSt = conn.prepareStatement(INSERT_AIRPORT);
				Iterator<AirportDto> it = airports != null ? airports.iterator()
						: null;
				AirportDto airportDto = null;
				while (it != null && it.hasNext()) {
					airportDto = it.next();
					inSt.setString(1, airportDto.getAirportCode());
					inSt.setString(2, airportDto.getAirportName());
					inSt.setString(3, airportDto.getCity());
					inSt.setString(4, airportDto.getCityCode());
					inSt.setString(5, airportDto.getCountryCode());
					inSt.setString(6, airportDto.getCountryName());
					inSt.setString(7, airportDto.getLatitude());
					inSt.setString(8, airportDto.getLongitude());
					inSt.addBatch();
				}
				inSt.executeBatch();
			}
			
		} catch (SQLException ex) {
			throw new PersistenceException("Error ocurred while inserting flights data", ex);
		} finally {
			try {
				if(inSt != null) inSt.close();
				if (rs != null) rs.close();
				if(st != null) st.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void insertAirline(Connection conn, AirlineDto airline) throws PersistenceException {
		PreparedStatement st = null;
		PreparedStatement inSt = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(SELECT_AIRLINE);
			st.setString(1, airline.getAirlineCode());
			rs = st.executeQuery();
			String airlineCode = null;
			while(rs != null && rs.next()) {
				airlineCode = rs.getString(1);
			}
			if(airlineCode == null || "".equals(airlineCode.trim())) {
				inSt = conn.prepareStatement(INSERT_AIRLINE);				
				inSt.setString(1, airline.getAirlineCode());
				inSt.setString(2, airline.getAirlineName());
				inSt.setString(3, airline.getPhoneNumber());
				inSt.executeUpdate();
			}
		} catch (SQLException ex) {
			throw new PersistenceException("Error ocurred while inserting ariports data", ex);
		} finally {
			try {
				if (rs != null) rs.close();
				if(st != null) st.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
