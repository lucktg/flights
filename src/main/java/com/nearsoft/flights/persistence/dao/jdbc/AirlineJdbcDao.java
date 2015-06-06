package com.nearsoft.flights.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nearsoft.flights.domain.model.flight.Airline;
import com.nearsoft.flights.persistence.dao.AirlineDao;
import com.nearsoft.flights.persistence.dao.jdbc.utils.JdbcUtils;

@Component
public class AirlineJdbcDao implements AirlineDao {
	
	@Autowired
	private DataSource datasource;
	
	private static final String SELECT_ALL = "SELECT AIRLINE_CODE, AIRLINE_NAME, PHONE_NUMBER FROM AIRLINE";
	private static final String SELECT_BY_AIRLINE_CODE = SELECT_ALL + " WHERE AIRLINE_CODE =?";
	private static final String INSERT = "INSERT INTO AIRLINE (AIRLINE_CODE, AIRLINE_NAME,PHONE_NUMBER) VALUES(?,?,?)";
	private static final String DELETE = "DELETE FROM AIRLINE";
	
	@Override
	public Airline findByAirlineCode(Connection conn, String airlineCode) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(SELECT_BY_AIRLINE_CODE);
			st.setString(1, airlineCode);
			rs = st.executeQuery();
			Airline airline = null;
			while (rs != null && rs.next()) {
				airline = extractAirline(rs);
			}
			return airline;
		} catch (SQLException ex) {
			throw new PersistenceException("Error occured while fetching airline data", ex);
		} finally {
			JdbcUtils.close(st, rs);
		}
	}

	
	private void insert(Connection conn, Airline airline) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(INSERT);
			fillStatement(airline, st);
			st.executeUpdate();
		} catch (SQLException ex) {
			throw new PersistenceException("Error ocurred while inserting airline data", ex);
		} finally {
			JdbcUtils.close(conn, st);
		}
	}
	
	@Override
	public void safeInsert(Connection conn, Airline airline) {
		Airline found = findByAirlineCode(conn, airline.getAirlineCode());
		if(found == null || found.getAirlineCode() == null || "".equals(found.getAirlineCode())) {
			insert(conn,airline);
		}
	}

	@Override
	public void deleteAll(Connection conn) throws PersistenceException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(DELETE);
			st.executeUpdate();
		} catch (SQLException ex) {
			throw new PersistenceException("Error ocurred while inserting airline data", ex);
		} finally {
			JdbcUtils.close(st);
		}
		
	}
	
	private Airline extractAirline(ResultSet rs) throws SQLException {
		return new Airline(rs.getString(1), rs.getString(3), rs.getString(2), null);
	}
	
	private void fillStatement(Airline airline, PreparedStatement st)
			throws SQLException {
		st.setString(1, airline.getAirlineCode());
		st.setString(2, airline.getName());
		st.setString(3, airline.getPhoneNumber());
	}

}
