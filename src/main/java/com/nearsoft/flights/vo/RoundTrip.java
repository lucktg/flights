package com.nearsoft.flights.vo;

import java.util.Set;

public class RoundTrip {
	
	private Set<Flight> originFlights;
	
	private Set<Flight> destinyFlights;
	
	public RoundTrip(Set<Flight> originFlights, Set<Flight> destinyFlights) {
		this.originFlights = originFlights;
		this.destinyFlights = destinyFlights;
	}

	public Set<Flight> getOriginFlights() {
		return originFlights;
	}
	
	public Set<Flight> getDestinyFlights() {
		return destinyFlights;
	}
}
