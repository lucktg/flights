package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.util.Set;


public class AppendixPojo {
	
	private Set<AirlinePojo> airlines;
	private Set<AirportPojo> airports;
	private Set<EquipmentPojo> equipments;
	
	public AppendixPojo() {
		
	}

	protected Set<AirlinePojo> getAirlines() {
		return airlines;
	}

	protected Set<AirportPojo> getAirports() {
		return airports;
	}

	protected Set<EquipmentPojo> getEquipments() {
		return equipments;
	}
	
	
	
	
}