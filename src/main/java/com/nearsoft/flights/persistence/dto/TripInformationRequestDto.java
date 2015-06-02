package com.nearsoft.flights.persistence.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

public class TripInformationRequestDto implements Serializable {
	private static final Calendar calendar = Calendar.getInstance();
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1855336546221103944L;
	
	private String departureAirportCode;
	private String arrivalAirportCode;
	private Timestamp departureDate;
	
	public String getDepartureAirportCode() {
		return departureAirportCode;
	}
	public void setDepartureAirportCode(String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}
	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}
	public void setArrivalAirportCode(String arrivalAirportCode) {
		this.arrivalAirportCode = arrivalAirportCode;
	}
	public Timestamp getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Timestamp departureDate) {
		this.departureDate = departureDate;
	}
	
	public Timestamp getDepartureDateEndDay() {
		calendar.setTimeInMillis(departureDate.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return new Timestamp(calendar.getTimeInMillis());
	}

	
}