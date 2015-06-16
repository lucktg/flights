package com.nearsoft.flights.domain.repository;

import com.nearsoft.flights.domain.model.Airline;
import com.nearsoft.flights.domain.repository.Repository;

public interface AirlineRepository extends Repository<Airline>{

	Airline getByAirlineCode(String airlineCode);
}
