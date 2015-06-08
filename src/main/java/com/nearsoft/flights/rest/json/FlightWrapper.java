package com.nearsoft.flights.rest.json;

import java.util.Set;

import com.nearsoft.flights.domain.model.flight.Airline;
import com.nearsoft.flights.domain.model.flight.Flight;

public class FlightWrapper {	

	private Flight flight;
	private ScheduledTripWrapper departure,arrival;
	
	
	public FlightWrapper(Flight flight) {
		this.flight = flight;
		this.departure = new ScheduledTripWrapper(flight.getDeparture());
		this.arrival = new ScheduledTripWrapper(flight.getArrival());
	}
	
	public Set<String> getServiceClass() {
		return flight.getServiceClass();
	}
	
	public String getServiceType() {
		return flight.getServiceType();
	}
	
	public Airline getAirline() {
		return flight.getAirline();
	}
	
	public ScheduledTripWrapper getDeparture() {
		return departure;
	}
	
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
