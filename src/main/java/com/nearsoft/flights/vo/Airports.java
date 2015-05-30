package com.nearsoft.flights.vo;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Airports extends FlightItemsSet<AirportDTO>{
	@JsonProperty("airports")
	private Set<AirportDTO> airports;
	
	public Airports() {

	}
	
	public Airports(Set<AirportDTO> airports) {
		this.airports = airports;
	}

	@Override
	public Set<AirportDTO> getItems() {
		return airports;
	}
}
