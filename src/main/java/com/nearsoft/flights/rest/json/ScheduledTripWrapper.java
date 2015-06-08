package com.nearsoft.flights.rest.json;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.flight.ScheduledTrip;

public class ScheduledTripWrapper {

	private ScheduledTrip scheduledTrip;
	
	public ScheduledTripWrapper(ScheduledTrip scheduledTrip){
		this.scheduledTrip = scheduledTrip;
	}
	
	public Airport getAirport() {
		return scheduledTrip.getAirport();
	}
	@JsonSerialize(using=JsonCustomDateSerializer.class)
	public Date getScheduledDate() {
		return scheduledTrip.getScheduledDate();
	}
	
	public String getTerminal() {
		return scheduledTrip.getTerminal();
	}
}
