package com.nearsoft.flights.vo;

import java.util.Date;

public class TripInformation {
	private String departureAirportCode;
	private String arrivalAirportCode;
	private Date departureDate;

	public TripInformation(String departureAirportCode,
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