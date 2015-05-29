package com.nearsoft.flights.vo;

import java.util.Date;

public class TripInformationRequest {
	private String departureAirportCode;
	private String arrivalAirportCode;
	private Date departureDate;

	public TripInformationRequest(String departureAirportCode,
			String arrivalAirportCode, Date departureDate) {
		this.departureAirportCode = departureAirportCode;
		this.arrivalAirportCode = arrivalAirportCode;
		this.departureDate = departureDate;
	}

	public String getDepartureAirportCode() {
		return departureAirportCode;
	}

	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}

	public Date getDepartureDate() {
		return departureDate;
	}
}