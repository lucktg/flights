package com.nearsoft.flights.vo;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Flights extends FlightItemsSet<Flight> {
	
	@JsonProperty("scheduledFlights")
	private Set<Flight> flights;
	
	public Flights() {
		
	}

	public Flights(Set<Flight> flights) {
		this.flights = flights;
	}

	public Set<Flight> getItems(){
		return flights;
	}
	
}
