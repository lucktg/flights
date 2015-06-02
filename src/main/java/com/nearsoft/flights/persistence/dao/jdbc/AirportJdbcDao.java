package com.nearsoft.flights.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.sql.DataSource;

import com.nearsoft.flights.persistence.dao.AirportDao;
import com.nearsoft.flights.persistence.dto.AirportDto;

public class AirportJdbcDao implements AirportDao {

	private DataSource datasource;
	
	private static final String INSERT = "INSERT INTO AIRPORT (AIRPORT_CODE,AIRPORT_NAME,CITY,CITY_CODE,COUNTRY_CODE,COUNTRY_NAME,LATITUDE,LONGITUDE) VALUES (?,?,?,?,?,?,?,?)";
	private static final String SELECT = "SELECT AIRPORT_CODE,AIRPORT_NAME,CITY,CITY_CODE,COUNTRY_CODE,COUNTRY_NAME,LATITUDE,LONGITUDE FROM AIRPORT";
	private static final String FIND_BY_AIRPORT_CODE = "SELECT AIRPORT_CODE,AIRPORT_NAME,CITY,CITY_CODE,COUNTRY_CODE,COUNTRY_NAME,LATITUDE,LONGITUDE FROM AIRPORT WHERE AIRPORT_CODE=?";
	private static final String DELETE = "DELETE FROM AIRPORT";
	
	public AirportJdbcDao(DataSource datasource) {
		this.datasource = datasource;
	}
	
	@Override
	public void insert(AirportDto airportDto) throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(INSERT);
			setAirportDto(st, airportDto);
			st.executeUpdate();
		} catch (SQLException ex) {
			throw new PersistenceException("Error occured while insert airport data ["+airportDto+"]", ex);
		} finally {
			try {
			if(st != null) st.close();
			if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	@Override
	public void insert(Set<AirportDto> airports) throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(INSERT);
			Iterator<AirportDto> it = airports!= null ? airports.iterator() : null;
			AirportDto airportDto = null;
			while(it != null && it.hasNext()) {
				airportDto = it.next();
				setAirportDto(st, airportDto);
				st.addBatch();
			}
			st.executeBatch();
		} catch (SQLException ex) {
			throw new PersistenceException("Error occured while insert airport data set", ex);
		} finally {
			try {
			if(st != null) st.close();
			if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	@Override
	public void deleteAll() throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(DELETE);
			st.executeUpdate();
		} catch(SQLException ex) {
			throw new PersistenceException("Error occured while fetching airport data",ex);
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
	public Set<AirportDto> findAll() throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(SELECT);
			rs  = st.executeQuery();
			Set<AirportDto> airports = new HashSet<AirportDto>();
			while(rs != null && rs.next()){
				airports.add(extractDto(rs));
			}
			return airports;
		} catch(SQLException ex) {
			throw new PersistenceException("Error occured while fetching airport data",ex);
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
	public AirportDto findByAirportCode(String airportCode) throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(FIND_BY_AIRPORT_CODE);
			st.setString(1, airportCode);
			rs  = st.executeQuery();
			AirportDto airportDto = null;
			while(rs != null && rs.next()){
				airportDto = extractDto(rs);
			}
			return airportDto;
		} catch(SQLException ex) {
			throw new PersistenceException("Error occured while fetching airport data",ex);
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
	
	private AirportDto extractDto(ResultSet rs) throws SQLException {
		AirportDto airport = new AirportDto();
		airport.setAirportCode(rs.getString(1));
		airport.setAirportName(rs.getString(2));
		airport.setCity(rs.getString(3));
		airport.setCityCode(rs.getString(4));
		airport.setCountryCode(rs.getString(5));
		airport.setCountryName(rs.getString(6));
		airport.setLatitude(rs.getString(7));
		airport.setLongitude(rs.getString(8));
		return airport;
	}
	
	private void setAirportDto(PreparedStatement st, AirportDto airportDto)
			throws SQLException {
		st.setString(1, airportDto.getAirportCode());
		st.setString(2, airportDto.getAirportName());
		st.setString(3, airportDto.getCity());
		st.setString(4, airportDto.getCityCode());
		st.setString(5, airportDto.getCountryCode());
		st.setString(6, airportDto.getCountryName());
		st.setString(7, airportDto.getLatitude());
		st.setString(8, airportDto.getLongitude());
	}

}
