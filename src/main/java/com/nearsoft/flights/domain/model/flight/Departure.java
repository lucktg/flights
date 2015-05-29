package com.nearsoft.flights.domain.model.flight;

import java.util.Date;

import com.nearsoft.flights.domain.model.airport.Airport;

public class Departure {
	private Airport airport;
	private Date departureDate;
	private String terminal;
	
	public Airport getAirport() {
		return airport;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	
	public String getTerminal() {
		return terminal;
	}
	
	
}
