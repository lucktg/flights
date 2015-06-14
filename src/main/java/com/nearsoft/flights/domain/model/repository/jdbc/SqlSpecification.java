package com.nearsoft.flights.domain.model.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface SqlSpecification<T> {
	
	PreparedStatement fillPreparedStatement(Connection conn);

	T fillModelObject(ResultSet rs, int rowNum);

}
