package com.nearsoft.flights.domain.model.airport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;

@Repository
public class JdbcAirportRepository implements AirportRepository {
	private static final String INSERT = "INSERT INTO AIRPORT (AIRPORT_CODE,AIRPORT_NAME,CITY,CITY_CODE,COUNTRY_CODE,COUNTRY_NAME,LATITUDE,LONGITUDE) VALUES (?,?,?,?,?,?,?,?)";
	private static final String SELECT = "SELECT AIRPORT_CODE,AIRPORT_NAME,CITY,CITY_CODE,COUNTRY_CODE,COUNTRY_NAME,LATITUDE,LONGITUDE FROM AIRPORT";
	private static final String FIND_BY_AIRPORT_CODE = "SELECT AIRPORT_CODE,AIRPORT_NAME,CITY,CITY_CODE,COUNTRY_CODE,COUNTRY_NAME,LATITUDE,LONGITUDE FROM AIRPORT WHERE AIRPORT_CODE=?";
	private static final String DELETE = "DELETE FROM AIRPORT";
	
	@Autowired
	private DataSource datasource;
	
	@Override
	public void add(Airport airport) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(INSERT);
			fillPreparedStatement(st, airport);
			st.executeUpdate();
		} catch (SQLException ex) {
			throw new PersistenceException("Error occured while insert airport data ["+airport+"].", ex);
		} finally {
			try {
			if(st != null) st.close();
			if(conn != null) conn.close();
			} catch (SQLException e) {
				throw new PersistenceException("Error occured while closing database resources.", e);
			}
		}		
	}
	
	
	@Override
	public void add(Set<Airport> airports) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(INSERT);
			for(Iterator<Airport> it = airports != null ? airports.iterator() : null; it != null && it.hasNext();){
				fillPreparedStatement(st, it.next());
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
					throw new PersistenceException("Error occured while closing database resources", e);
				}
		}		
	}

	@Override
	public void removeAll() {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(DELETE);
			st.executeUpdate();
		} catch(SQLException ex) {
			throw new PersistenceException("Error occured while fetching airport data.", ex);
		} finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				throw new PersistenceException("Error occured while closing database resources.", e);
			}
		}
	}

	

	@Override
	public Set<Airport> findAllActiveAirports() {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(SELECT);
			rs  = st.executeQuery();
			Set<Airport> airports = new HashSet<Airport>();
			
			while(rs != null && rs.next()){
				airports.add(extractAirport(rs));
			}
			return airports;
		} catch(SQLException ex) {
			throw new PersistenceException("Error occured while fetching airport data", ex);
		} finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				throw new PersistenceException("Error occured while closing database resources.", e);
			}
		}
	}

	@Override
	public Airport findByAirportCode(String airportCode) throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(FIND_BY_AIRPORT_CODE);
			st.setString(1, airportCode);
			rs  = st.executeQuery();
			Airport airport = null;
			while(rs != null && rs.next()){
				airport = extractAirport(rs);
			}
			return airport;
		} catch(SQLException ex) {
			throw new PersistenceException("Error occured while fetching airport data", ex);
		} finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				throw new PersistenceException("Error occured while closing database resoruces", e);
			}
		}
	}
	
	private Airport extractAirport(ResultSet rs) throws SQLException {
		AirportBuilder builder = new AirportBuilder(rs.getString(1));
		builder.addName(rs.getString(2));
		builder.addCity(rs.getString(3));
		builder.addCityCode(rs.getString(4));
		builder.addCountryCode(rs.getString(5));
		builder.addCountryName(rs.getString(6));
		builder.addLatitude(rs.getString(7));
		builder.addLongitude(rs.getString(8));
		return builder.build();
	}
	
	private void fillPreparedStatement(PreparedStatement st, Airport airport)
			throws SQLException {
		st.setString(1, airport.getAirportCode());
		st.setString(2, airport.getName());
		st.setString(3, airport.getCity());
		st.setString(4, airport.getCityCode());
		st.setString(5, airport.getCountryCode());
		st.setString(6, airport.getCountryName());
		st.setString(7, airport.getLatitude());
		st.setString(8, airport.getLongitude());
	}
}
