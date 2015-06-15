package com.nearsoft.flights.domain.model.repository;

import java.util.List;
import java.util.Set;

import com.nearsoft.flights.domain.model.repository.jdbc.specification.SqlSpecification;

public interface Repository<T> {
	void add(T t);
	void addAll(Set<T> t);
	void remove(T t);
	void removeAll();
	void update(T t);
	List<T> getAll();
	List<T> getAllBySpecification(SqlSpecification sqlSpecification);
	T getBySpecification(SqlSpecification sqlSpecification);
}

