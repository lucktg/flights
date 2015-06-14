package com.nearsoft.flights.domain.model.repository.jdbc;

import static com.nearsoft.flights.util.Utils.isNull;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.nearsoft.flights.domain.model.repository.ForeignKey;
import com.nearsoft.flights.domain.model.repository.IgnorePersistence;
import com.nearsoft.flights.domain.model.repository.IgnorePersistence.Operation;
import com.nearsoft.flights.domain.model.repository.Repository;
import com.nearsoft.flights.domain.model.repository.Table;

public abstract class JdbcRepository<T> implements Repository<T> {
	
	private static final Logger logger = Logger.getLogger(JdbcRepository.class);
	
	private final String tableName;
	private final String[]  columnNames;
	private final String[] updateColumNames;
	private final String[] idTable;
	private Class<T> clazz;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public JdbcRepository(Class<T> clazz) {
		this.clazz = clazz;
		Table a = this.clazz.getAnnotation(Table.class);
		if (isNull(a)) throw new NullPointerException("@Table annotation is missing");
		idTable = a.idTable();
		tableName = this.clazz.getSimpleName();
		Field[] fields = this.clazz.getDeclaredFields();
		columnNames = Arrays.stream(fields)
			.sorted((first, second) -> first.getName().compareTo(second.getName()))
			.filter( p -> ignoreField(p, Operation.INSERT))
			.map(p -> {
				if(p.isAnnotationPresent(ForeignKey.class)) {
					Table t = p.getType().getAnnotation(Table.class);
					logger.debug(t.idTable());
					try {
						return p.getType().getDeclaredField(t.idTable()[0]);
					} catch(Exception e) {
						throw new RuntimeException(e);
					}
				} else return p;
			} )
			.map(p -> camelCaseDash(p.getName())).collect(Collectors.toList())
			.toArray(new String[0]);
		updateColumNames = Arrays.stream(fields)
				.sorted((first, second) -> first.getName().compareTo(second.getName()))
				.filter( p -> ignoreField(p,Operation.UPDATE))
				.map(p -> camelCaseDash(p.getName())).collect(Collectors.toList())
				.toArray(new String[0]);
	}
	
	public abstract void fillInsertPreparedStatement(PreparedStatement ps, T t) throws SQLException;
	public abstract void fillUpdatePreparedStatement(PreparedStatement ps, T t) throws SQLException;
	public abstract void fillDeletePreparedStatement(PreparedStatement ps, T t) throws SQLException;	
	public abstract T fillModelObject(ResultSet rs, int rowNum) throws SQLException;
	
	public void add(T t) {
		String sql = buildInsertStatement();
		logger.debug("Inserting element with SQL statement  [" + sql + "] and values ["+t+"]");
		jdbcTemplate.update(sql, (ps) -> fillInsertPreparedStatement(ps,t));
	}
	
	public void addAll(Set<T> t) {
		String sql = buildInsertStatement();
		logger.debug("Inserting elements with SQL statement  [" + sql + "] and values ["+t+"]");
		final List<T> list = t.stream().collect(Collectors.toList());
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				T tList = list.get(i);
				fillInsertPreparedStatement(ps,tList);
			}
	
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
	
	
	@Override
	public void remove(T t) {
		String sql = buildDeleteStatement();
		logger.debug("Deleting element with SQL statement  [" + sql + "] and values ["+t+"]");
		jdbcTemplate.update(sql, ps -> fillDeletePreparedStatement(ps, t));
	}
	
	@Override
	public void removeAll() {
		String sql = buildDeleteAllStatement();
		logger.debug("Deleting all elements with SQL statement  [" + sql + "]");
		jdbcTemplate.update(sql);
	}

	@Override
	public void update(T t) {
		String sql = buildUpdateStatement();
		logger.debug("Updating element with SQL statement  [" + sql + "] and values ["+t+"]");
		jdbcTemplate.update(sql, ps -> fillUpdatePreparedStatement(ps, t));
	}

	@Override
	public List<T> getAllBySpecification(SqlSpecification<T> sqlSpecification) {
		return jdbcTemplate.query((conn) -> sqlSpecification.fillPreparedStatement(conn), 
				(rs, rowNum) -> sqlSpecification.fillModelObject(rs, rowNum));
	}

	public String[] getUpdateColumnNames() {
		return updateColumNames;
	}
	
	@Override
	public List<T> getAll() {
		String query = buildSelectAllStatement();
		logger.debug("Getting all elements with SQL statement ["+query+"]");
		return jdbcTemplate.query(buildSelectAllStatement(), (rs, rowNum) -> fillModelObject(rs, rowNum));
	}

	public String[] getIdColumns() {
		return idTable;
	}
	
	public String[] getColumnNames() {
		return columnNames;
	}
	
	private static boolean ignoreField(Field p, Operation operation) {
		IgnorePersistence ignoreAnotation = p.getAnnotation(IgnorePersistence.class);
		if (isNull(ignoreAnotation) || 
				!(ignoreAnotation.ignore().equals(Operation.ALL) ||
				ignoreAnotation.ignore().equals(operation))) return true;
		return false;
	}
	
	private String buildInsertStatement() {
		String[] columnNames = getColumnNames();
		return "INSERT INTO " +  tableName + 
				"("+Arrays.stream(columnNames).collect(Collectors.joining(","))+") VALUES (" + 
				Collections.nCopies(columnNames.length, "?").stream().collect(Collectors.joining(",")) + ")";
	}
	
	private String buildUpdateStatement() {
		String[] columnNames = getUpdateColumnNames();
		return "UPDATE " +  tableName + " SET " +
				Arrays.stream(columnNames).map(p ->  p +"= ? ").collect(Collectors.joining(","))+" where " + getIdClausule();
	}
	
	private String buildDeleteStatement() {
		return buildDeleteAllStatement() +  " where " + getIdClausule();
	}
	
	private String buildDeleteAllStatement() {
		return "DELETE FROM " +  tableName ;
	}
	
	private String buildSelectAllStatement() {
		String[] columnNames = getColumnNames();
		return "SELECT " +Arrays.stream(columnNames).collect(Collectors.joining(",")) +  " FROM " + tableName;
	}
	
	private String getIdClausule() {
		return Arrays.stream(getIdColumns()).map(p-> camelCaseDash(p) + "=?").collect(Collectors.joining(" and "));
	}
	
	private static String camelCaseDash(String camelCase) {
		StringBuffer buff = new StringBuffer();
		camelCase.chars().mapToObj(p -> (char) p).forEach( p -> {
			if (Character.isUpperCase(p)) 
				buff.append('_').append(Character.toLowerCase(p));
			else
				buff.append(p);
		});
		return buff.toString();
	}

}
