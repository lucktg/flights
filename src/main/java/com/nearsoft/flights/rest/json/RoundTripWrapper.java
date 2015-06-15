package com.nearsoft.flights.rest.json;

import java.util.Set;

public class RoundTripWrapper {

	private Set<FlightWrapper> origin;
	private Set<FlightWrapper> destiny;
	
	public RoundTripWrapper(){
		
	}
	
	
	public RoundTripWrapper(Set<FlightWrapper> origin,
			Set<FlightWrapper> destiny) {
		this.origin = origin;
		this.destiny = destiny;
	}
	public Set<FlightWrapper> getOrigin() {
		return origin;
	}
	public Set<FlightWrapper> getDestiny() {
		return destiny;
	}
	
	
}
