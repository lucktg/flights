package com.nearsoft.flights.domain.model.flight;

import java.util.LinkedHashSet;

public class Flight {
	
	private String flightNumber;
	private LinkedHashSet<Flight> connectionFlights;
	private Airline airline;
	private Departure departure;
	private Arrival arrival;
	
	public String getFlightNumber() {
		return flightNumber;
	}
	
	public Airline getAirline() {
		return airline;
	}
	
	public Departure getDeparture() {
		return departure;
	}
	
	public Arrival getArrival() {
		return arrival;
	}
	
	public boolean hasStops(){
		return !(connectionFlights != null && connectionFlights.isEmpty());
	}
	
	public void addConnectingFlight(Flight flight){
		connectionFlights.add(flight);
	}

}
