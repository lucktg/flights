package com.nearsoft.flights.vo;

import java.util.Set;

public class RoundTrip {
	
	private Set<FlightDto> originFlights;
	
	private Set<FlightDto> destinyFlights;
	
	public RoundTrip(Set<FlightDto> originFlights, Set<FlightDto> destinyFlights) {
		this.originFlights = originFlights;
		this.destinyFlights = destinyFlights;
	}

	public Set<FlightDto> getOriginFlights() {
		return originFlights;
	}
	
	public Set<FlightDto> getDestinyFlights() {
		return destinyFlights;
	}
}
