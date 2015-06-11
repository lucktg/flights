package com.nearsoft.flights.interfaces.flexapi.domain.model;

import java.util.Set;


public class AppendixPojo {
	
	private Set<AirlinePojo> airlines;
	private Set<AirportPojo> airports;
	private Set<EquipmentPojo> equipments;
	
	public AppendixPojo() {
		
	}

	public Set<AirlinePojo> getAirlines() {
		return airlines;
	}

	public Set<AirportPojo> getAirports() {
		return airports;
	}

	public Set<EquipmentPojo> getEquipments() {
		return equipments;
	}
	
	
	
	
}