package com.nearsoft.flights.interfaces.flexapi.domain.services;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nearsoft.flights.interfaces.flexapi.domain.model.AppendixPojo;
import com.nearsoft.flights.interfaces.flexapi.domain.model.FlightScheduledPojo;

public class FlightJsonSetWrapper {
	@JsonProperty("scheduledFlights")
	 Set<FlightScheduledPojo> flights;
	 @JsonProperty("appendix")
	 AppendixPojo appendix;
	 
	 public FlightJsonSetWrapper(){
		 
	 }
	 
	 public Set<FlightScheduledPojo> getFlights() {
		return flights;
	}
	 
	 public AppendixPojo getAppendix() {
		return appendix;
	}
}
