package com.nearsoft.flights.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.sql.DataSource;

import com.nearsoft.flights.persistence.dao.AirlineDao;
import com.nearsoft.flights.persistence.dto.AirlineDto;

public class AirlineJdbcDao implements AirlineDao {
	
	private DataSource datasource;
	
	private static final String SELECT_ALL = "SELECT AIRLINE_CODE, AIRLINE_NAME, PHONE_NUMBER FROM AIRLINE";
	private static final String SELECT_BY_AIRLINE_CODE = SELECT_ALL + " WHERE AIRLINE_CODE =?";
	private static final String INSERT = "INSERT INTO AIRLINE (AIRLINE_CODE, AIRLINE_NAME,PHONE_NUMBER) VALUES(?,?,?)";
	private static final String DELETE = "DELETE FROM AIRLINE";
	
	public AirlineJdbcDao(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public Set<AirlineDto> findAll() throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(SELECT_ALL);
			rs = st.executeQuery();
			Set<AirlineDto> airlines = new HashSet<>();
			AirlineDto dto = null;
			while (rs != null && rs.next()) {
				dto = extractDto(rs);
				airlines.add(dto);
			}
			return airlines;
		} finally {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
		}
	}

	@Override
	public AirlineDto findByAirlineCode(String airlineCode) throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(SELECT_BY_AIRLINE_CODE);
			st.setString(1, airlineCode);
			rs = st.executeQuery();
			AirlineDto dto = null;
			while (rs != null && rs.next()) {
				dto = extractDto(rs);
			}
			return dto;
		} catch (SQLException ex) {
			throw new PersistenceException("Error occured while fetching airline data", ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void insert(Set<AirlineDto> airlines) throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(INSERT);
			Iterator<AirlineDto> it = airlines != null ? airlines.iterator()
					: null;
			AirlineDto dto = null;
			while (it != null && it.hasNext()) {
				dto = it.next();
				fillStatement(dto, st);
				st.addBatch();
			}
			st.executeBatch();
		} catch (SQLException ex) {
			throw new PersistenceException("", ex);
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

	@Override
	public void insert(AirlineDto airline) throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(INSERT);
			fillStatement(airline, st);
			st.executeUpdate();
		} catch (SQLException ex) {
			throw new PersistenceException("Error ocurred while inserting airline data", ex);
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
	

	@Override
	public void deleteAll() throws PersistenceException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = datasource.getConnection();
			st = conn.prepareStatement(DELETE);
			st.executeUpdate();
		} catch (SQLException ex) {
			throw new PersistenceException("Error ocurred while inserting airline data", ex);
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
	
	private AirlineDto extractDto(ResultSet rs) throws SQLException {
		AirlineDto dto;
		dto = new AirlineDto();
		dto.setAirlineCode(rs.getString(1));
		dto.setAirlineName(rs.getString(2));
		dto.setPhoneNumber(rs.getString(3));
		return dto;
	}
	
	private void fillStatement(AirlineDto airline, PreparedStatement st)
			throws SQLException {
		st.setString(1, airline.getAirlineCode());
		st.setString(2, airline.getAirlineName());
		st.setString(3, airline.getPhoneNumber());
	}

}
