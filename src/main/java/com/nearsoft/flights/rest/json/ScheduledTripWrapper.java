package com.nearsoft.flights.rest.json;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.flight.ScheduledTrip;

public class ScheduledTripWrapper {

	private ScheduledTrip scheduledTrip;
	public ScheduledTripWrapper() {
		
	}
	public ScheduledTripWrapper(ScheduledTrip scheduledTrip){
		this.scheduledTrip = scheduledTrip;
	}
	@JsonProperty	
	public Airport getAirport() {
		return scheduledTrip.getAirport();
	}
	@JsonSerialize(using=JsonCustomDateSerializer.class)
	public Date getScheduledDate() {
		return scheduledTrip.getScheduledDate();
	}
	@JsonProperty
	public String getTerminal() {
		return scheduledTrip.getTerminal();
	}
}
