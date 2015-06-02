package com.nearsoft.flights.vo;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Flights extends FlightItemsSet<FlightDto> {
	
	@JsonProperty("scheduledFlights")
	private Set<FlightDto> flights;
	
	public Flights() {
		
	}

	public Flights(Set<FlightDto> flights) {
		this.flights = flights;
	}

	public Set<FlightDto> getItems(){
		return flights;
	}
	
}
