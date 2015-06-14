package com.nearsoft.flights.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.persistence.dao.AirportDao;
import com.nearsoft.flights.persistence.dao.jdbc.utils.JdbcUtils;

@Component
public class AirportJdbcDao implements AirportDao {

	private static final Logger logger = Logger.getLogger(AirportJdbcDao.class);
	@Autowired
	private DataSource datasource;
	
	private static final String INSERT = "INSERT INTO AIRPORT (AIRPORT_CODE,AIRPORT_NAME,CITY,CITY_CODE,COUNTRY_CODE,COUNTRY_NAME,LATITUDE,LONGITUDE) VALUES (?,?,?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM AIRPORT";
	private static final String SELECT_AIRPORT = "SELECT AIRPORT_CODE, LATITUDE, LONGITUDE FROM AIRPORT WHERE AIRPORT_CODE IN (?)";

	
	public void safeInsert(Connection conn, Set<Airport> airports) {
		logger.debug("Inserting airports ["+airports+"], verifying if already exist");
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			List<String> params = Collections.nCopies(airports.size(), "?");
			String str = params.stream().collect(Collectors.joining(","));
			st = conn.prepareStatement(SELECT_AIRPORT.replace("?", str));
			int index=1;
			for(Airport airport:airports) {
				st.setString(index++, airport.getAirportCode());
			}
			rs = st.executeQuery();
			while(rs != null && rs.next()) {
				AirportBuilder builder = new AirportBuilder(rs.getString(1));
				builder.addLatitude(rs.getString(2));
				builder.addLongitude(rs.getString(3));
				airports.remove(builder.build());
			}
			if (airports != null && !airports.isEmpty()) insert(conn, airports);
		} catch(SQLException ex) {
			logger.error(ex);
			throw new PersistenceException("Error occured while insert airport data.", ex);
		} finally {
			JdbcUtils.close(st, rs);
		}
			
	}
	
	public void insert(Connection conn, Set<Airport> airports) {
		logger.debug("Inserting airports ["+airports+"]");
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(INSERT);
			Iterator<Airport> it = airports!= null ? airports.iterator() : null;
			Airport airportDto = null;
			while(it != null && it.hasNext()) {
				airportDto = it.next();
				fillPreparedStatement(st, airportDto);
				st.addBatch();
			}
			st.executeBatch();
		} catch (SQLException ex) {
			throw new PersistenceException("Error occured while insert airport data.", ex);
		} finally {
			JdbcUtils.close(st);
		}		
	}

	@Override
	public void deleteAll(Connection conn) throws PersistenceException {
		logger.debug("Deleting airports");
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(DELETE);
			st.executeUpdate();
		} catch(SQLException ex) {
			throw new PersistenceException("Error occured while fetching airport data",ex);
		} finally {
			JdbcUtils.close(st);
		}
	}

	
	private void fillPreparedStatement(PreparedStatement st, Airport airport)
			throws SQLException {
		st.setString(1, airport.getAirportCode());
		st.setString(2, airport.getAirportName());
		st.setString(3, airport.getCity());
		st.setString(4, airport.getCityCode());
		st.setString(5, airport.getCountryCode());
		st.setString(6, airport.getCountryName());
		st.setString(7, airport.getLatitude());
		st.setString(8, airport.getLongitude());
	}

	
	

}
