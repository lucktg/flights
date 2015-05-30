package com.nearsoft.flights.persistence.dao;

import java.util.Set;

import com.nearsoft.flights.persistence.dto.AirportDTO;

public interface AirportDAO {
	
	Set<AirportDTO> findActiveAirports();

	void updateAirports(Set<AirportDTO> travelItem);

}
