package com.nearsoft.flights.service;

import java.util.Set;

import com.nearsoft.flights.vo.AirportDTO;

public interface AirportsService {
	Set<AirportDTO> getActiveAirports();
	AirportDTO getAirportByCode(String airportCode);
}
