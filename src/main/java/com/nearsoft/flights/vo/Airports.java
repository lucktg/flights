package com.nearsoft.flights.vo;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Airports extends FlightItemsSet<Airport>{
	@JsonProperty("airports")
	private Set<Airport> airports;
	
	public Airports() {

	}
	
	public Airports(Set<Airport> airports) {
		this.airports = airports;
	}

	@Override
	public Set<Airport> getItems() {
		return airports;
	}
}
