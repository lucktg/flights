package com.nearsoft.flights.domain.model.flight;

import java.util.Date;

public class TripInformationRequest {
	private String departureAirportCode;
	private Date departureDate;
	private String arrivalAirportCode;
	
	public TripInformationRequest(String departureAirportCode,
			Date departureDate, String arrivalAirportCode) {
		super();
		this.departureAirportCode = departureAirportCode;
		this.departureDate = departureDate;
		this.arrivalAirportCode = arrivalAirportCode;
	}
	
	public String getDepartureAirportCode() {
		return departureAirportCode;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}

	@Override
	public String toString() {
		return "TripInformationRequest [departureAirportCode="
				+ departureAirportCode + ", departureDate=" + departureDate
				+ ", arrivalAirportCode=" + arrivalAirportCode + "]";
	}
	
	
	
}
