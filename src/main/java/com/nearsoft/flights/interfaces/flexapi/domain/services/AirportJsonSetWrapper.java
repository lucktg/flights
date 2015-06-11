package com.nearsoft.flights.interfaces.flexapi.domain.services;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nearsoft.flights.interfaces.flexapi.domain.model.AirportPojo;

public class AirportJsonSetWrapper {
	@JsonProperty("airports")
	private Set<AirportPojo> airports;
	
	//Required by jackson
	AirportJsonSetWrapper() {

	}
	
	public Set<AirportPojo> getAirports() {
		return airports;
	}
	
}