package com.nearsoft.flights.domain.repository.jdbc;

import static com.nearsoft.flights.util.Utils.isNull;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.nearsoft.flights.domain.repository.Repository;
import com.nearsoft.flights.domain.repository.jdbc.annotation.ForeignKey;
import com.nearsoft.flights.domain.repository.jdbc.annotation.IgnorePersistence;
import com.nearsoft.flights.domain.repository.jdbc.annotation.Table;
import com.nearsoft.flights.domain.repository.jdbc.annotation.IgnorePersistence.Operation;
import com.nearsoft.flights.domain.repository.jdbc.specification.SqlSpecification;

public abstract class JdbcRepository<T> implements Repository<T> {
	
	private static final Logger logger = Logger.getLogger(JdbcRepository.class);
	
	private final String tableName;
	private final String[]  columnNames;
	private final String[]  selectColumnNames;
	private final String[] updateColumNames;
	private final String[] idTable;
	private List<Field> fkList = new ArrayList<>();
	private Class<T> clazz;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public abstract void fillInsertPreparedStatement(PreparedStatement ps, T t) throws SQLException;
	public abstract void fillUpdatePreparedStatement(PreparedStatement ps, T t) throws SQLException;
	public abstract void fillDeletePreparedStatement(PreparedStatement ps, T t) throws SQLException;	
	public abstract T fillModelObject(ResultSet rs, int rowNum) throws SQLException;
	
	
	public JdbcRepository(Class<T> clazz) {
		this.clazz = clazz;
		Table a = this.clazz.getAnnotation(Table.class);
		if (isNull(a)) throw new NullPointerException("@Table annotation is missing");
		idTable = a.idTable();
		tableName = this.clazz.getSimpleName();
		Field[] fields = this.clazz.getDeclaredFields();
		columnNames = getColumns(fields, Operation.INSERT);
		updateColumNames = getColumns(fields, Operation.UPDATE);
		selectColumnNames = getSelectColumns(fields, Operation.INSERT, "");
	}

	private String[] getColumns(Field[] fields, Operation operation) {
		List<String> list = new ArrayList<String>();
		Arrays.stream(fields)
		//Ordered by field name
			.sorted((first, second) -> first.getName().compareTo(second.getName()))
			//Filtered by operation
			.filter( p -> ignoreField(p, operation)).forEach(p -> {
			//Verify if ForeignKey annotation is present
				if(p.isAnnotationPresent(ForeignKey.class)) {
					ForeignKey fk = p.getAnnotation(ForeignKey.class);
					//If Foreignkey annotation contains columns names, add values to list
					if(!isNull(fk.columns()) && fk.columns().length > 0){
						Arrays.stream(fk.columns()).forEach(f -> list.add(f));
					} else {
					//Otherwise the column name is extracted from idTable from @Table annotation
						Table table = p.getType().getAnnotation(Table.class);
						if(isNull(table)) throw new NullPointerException("@Table annotation is missing for @ForeignKey annotated element");
						if(isNull(table.idTable()) || table.idTable().length == 0) throw new NullPointerException("idTable in @Table annotation is missing for @ForeignKey annotated element");
						Arrays.stream(table.idTable()).forEach(a -> {
							try {
								list.add(camelCaseDash(p.getType().getDeclaredField(a).getName()));
							} catch (NoSuchFieldException | SecurityException e) {
								throw new RuntimeException(e);
							}
						});
					}
				//There is no ForeignKey annotation, so take the field name
				} else {
					list.add(camelCaseDash(p.getName()));
				}
			}); 
		return list.toArray(new String[list.size()]);
	}
	
	
	private String[] getSelectColumns(Field[] fields, Operation operation, String fieldName) {
		List<String> list = new ArrayList<String>();
		Arrays.stream(fields)
		//Ordered by field name
			.sorted((first, second) -> first.getName().compareTo(second.getName()))
			//Filtered by operation
			.filter( p -> ignoreField(p, operation)).forEach(p -> {
				String tableName = p.getDeclaringClass().getSimpleName();
			//Verify if ForeignKey annotation is present
				if(p.isAnnotationPresent(ForeignKey.class)) {
					ForeignKey fk = p.getAnnotation(ForeignKey.class);
					//If Foreignkey annotation contains columns names, add values to list
					if(!isNull(fk.columns()) && fk.columns().length > 0){
						Arrays.stream(fk.columns()).forEach(f -> list.add(tableName + "_"+fieldName+"."+f));
						fkList.add(p);
						list.addAll(Arrays.asList(getSelectColumns(p.getType().getDeclaredFields(), operation, p.getName())));
					} else {
					//Otherwise the column name is extracted from idTable from @Table annotation
						Table table = p.getType().getAnnotation(Table.class);
						if(isNull(table)) throw new NullPointerException("@Table annotation is missing for @ForeignKey annotated element");
						if(isNull(table.idTable()) || table.idTable().length == 0) throw new NullPointerException("idTable in @Table annotation is missing for @ForeignKey annotated element");
						Arrays.stream(table.idTable()).forEach(a -> {
							try {
								list.add(tableName + "_"+fieldName+"."+camelCaseDash(p.getType().getDeclaredField(a).getName()));
							} catch (NoSuchFieldException | SecurityException e) {
								throw new RuntimeException(e);
							}
						});
					}
				//There is no ForeignKey annotation, so take the field name
				} else {
					list.add(tableName + "_"+fieldName+"."+camelCaseDash(p.getName()));
				}
			}); 
		return list.toArray(new String[list.size()]);
	}
	
	
	public void add(T t) {
		String sql = buildInsertStatement();
		logger.debug("Inserting element with SQL statement  [" + sql + "] and values ["+t+"]");
		jdbcTemplate.update(sql, (ps) -> fillInsertPreparedStatement(ps,t));
	}
	
	@Override
	public void addAll(Set<T> t) {
		String sql = buildInsertStatement();
		if(idTable.length == 1) {
			String sqlIdsSearch = buildSelectAllStatement() + " where " +  camelCaseDash(idTable[0]) + " in (" + 
					(t.stream().map(p -> {
						try {
							Field id = clazz.getDeclaredField(idTable[0]);
							id.setAccessible(true);
							return "'" + id.get(p) + "'";
						} catch(IllegalArgumentException | IllegalAccessException  | NoSuchFieldException e) {
							throw new RuntimeException(e);
						}
					}).collect(Collectors.joining(",")))+")";
			logger.debug(sqlIdsSearch);
			List<T> found = jdbcTemplate.query(sqlIdsSearch, (rs, rowNum) -> fillModelObject(rs, rowNum));
			t.removeAll(found);
		}
		//logger.debug("Inserting elements with SQL statement  [" + sql + "] and values ["+t+"]");
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

	
	protected List<T> getAllBySpecification(SqlSpecification sqlSpecification) {
		String select = buildSelectAllStatement() + sqlSpecification.toSqlClauses();
		return jdbcTemplate.query(select, (rs, rowNum) -> fillModelObject(rs, rowNum));
	}
	
	protected T getBySpecification(SqlSpecification sqlSpecification) {
		String select = buildSelectAllStatement() + sqlSpecification.toSqlClauses();
		return jdbcTemplate.queryForObject(select, (rs, rowNum) -> fillModelObject(rs, rowNum));
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
		String[] columnNames = selectColumnNames;
		String cond  = fkList.stream().map(p -> {
			p.getType().getSimpleName() + "_"+p.getName()+"."+ camelCaseDash(p.getName())+ "="+tableName+"."+camelCaseDash(p.getName())).collect(Collectors.joining(" and "));
		
		});
		return "SELECT " +Arrays.stream(columnNames).collect(Collectors.joining(",")) +  " FROM " + tableName + " as " + tableName + "_, "+
			fkList.stream().map(p -> {
					Table table = p.getType().getAnnotation(Table.class);
					StringBuffer buffer = new StringBuffer();
					Arrays.stream(table.idTable()).forEach(a -> buffer.append(p.getType().getSimpleName() + " AS " +p.getType().getSimpleName() + "_"+p.getName()));
					return buffer.toString();
				}
				).collect(Collectors.joining(",")) + " where " + cond;
			
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
	
	private boolean ignoreField(Field p, Operation operation) {
		IgnorePersistence ignoreAnotation = p.getAnnotation(IgnorePersistence.class);
		if (isNull(ignoreAnotation) || 
				!(ignoreAnotation.ignore().equals(Operation.ALL) ||
				ignoreAnotation.ignore().equals(operation))) return true;
		return false;
	}

}
