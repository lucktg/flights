package com.nearsoft.flights.rest.json;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown=true)
public class RoundTripWrapper {

	private Set<FlightWrapper> originFlights;
	private Set<FlightWrapper> destinyFlights;
	
	public RoundTripWrapper(){
		
	}
	
	
	public RoundTripWrapper(Set<FlightWrapper> origin,
			Set<FlightWrapper> destiny) {
		this.originFlights = origin;
		this.destinyFlights = destiny;
	}
	public Set<FlightWrapper> getOriginFlights() {
		return originFlights;
	}
	public Set<FlightWrapper> getDestinyFlights() {
		return destinyFlights;
	}
	
	
}
