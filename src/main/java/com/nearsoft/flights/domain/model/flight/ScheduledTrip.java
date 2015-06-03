package com.nearsoft.flights.domain.model.flight;

import java.util.Date;

import com.nearsoft.flights.domain.model.airport.Airport;

public class ScheduledTrip {
	
	private Airport airport;
	private Date scheduledDate;
	private String terminal;
	
	public ScheduledTrip(Airport aiport, Date scheduledDate, String terminal) {
		this.airport = aiport;
		this.scheduledDate = scheduledDate;
		this.terminal = terminal;
	}
	
	public Airport getAirport() {
		return airport;
	}
	
	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getTerminal() {
		return terminal;
	}

}
