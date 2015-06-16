package com.nearsoft.flights.domain.repository;

import java.util.List;
import java.util.Set;

public interface Repository<T> {
	void add(T t);
	void addAll(Set<T> t);
	void remove(T t);
	void removeAll();
	void update(T t);
	List<T> getAll();
}

