package com.nearsoft.flights.domain.model.repository;

import java.util.List;

import com.nearsoft.flights.domain.model.repository.jdbc.SqlSpecification;

public interface Repository<T> {
	void add(T t);
	void remove(T t);
	void removeAll();
	void update(T t);
	List<T> getAll();
	List<T> getAllBySpecification(SqlSpecification<T> sqlSpecification);
}

