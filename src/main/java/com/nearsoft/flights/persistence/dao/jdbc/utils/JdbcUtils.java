package com.nearsoft.flights.persistence.dao.jdbc.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.nearsoft.flights.domain.model.exception.RepositoryException;

public class JdbcUtils {


	public static void close(Connection conn) {
		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			throw new RepositoryException("Error occured while closing database resources ", e);
		}
	}
	public static void close(Statement st) {
		try {
			if(st != null) st.close();
		} catch (SQLException e) {
			throw new RepositoryException("Error occured while closing database resources ", e);
		}
	}
	
	public static void close(Connection conn, Statement st) {
		try {
			if(st != null) st.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			throw new RepositoryException("Error occured while closing database resources ", e);
		}
	}
	
	public static void close(Connection conn, Statement st, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(st != null) st.close();
			if(conn != null) conn.close();
		} catch(SQLException e) {
			throw new RepositoryException("Error occured while closing database resources ", e);
		}
	}
	
	public static void close(Statement st, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(st != null) st.close();
		} catch(SQLException e) {
			throw new RepositoryException("Error occured while closing database resources ", e);
		}
	}
}
