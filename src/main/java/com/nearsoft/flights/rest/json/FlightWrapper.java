package com.nearsoft.flights.rest.json;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nearsoft.flights.domain.model.Airline;
import com.nearsoft.flights.domain.model.Flight;

public class FlightWrapper {	

	private Flight flight;
	private ScheduledTripWrapper departure,arrival;
	
	public FlightWrapper() {
		
	}
	
	public FlightWrapper(Flight flight) {
		this.flight = flight;
		this.departure = new ScheduledTripWrapper(flight.getDeparture());
		this.arrival = new ScheduledTripWrapper(flight.getArrival());
	}
	/*
	public Set<String> getServiceClass() {
		return flight.getServiceClass();
	}*/
	
	public String getServiceType() {
		return flight.getServiceType();
	}
	
	public Airline getAirline() {
		return flight.getAirline();
	}
	
	public ScheduledTripWrapper getDeparture() {
		return departure;
	}
	
	@JsonProperty("flightNumber")
	public String getFlightNumber() {
		return flight.getFlightNumber();
	}
	
	
	public ScheduledTripWrapper getArrival() {
		return arrival;
	}
	
	public boolean hasStops(){
		return flight.hasStops();
	}
	
	public void addConnectingFlight(Flight flight){
		flight.addConnectingFlight(flight);
	}
	
}
