package com.nearsoft.flights.domain.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TripInformationRequest {
	private static Calendar calendar = Calendar.getInstance();
	
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
		StringBuilder builder = new StringBuilder("TripInformationRequest [departureAirportCode=");
		builder.append(departureAirportCode)
		.append(", departureDate=").append(departureDate)
		.append(", arrivalAirportCode=").append(arrivalAirportCode).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((arrivalAirportCode == null) ? 0 : arrivalAirportCode
						.hashCode());
		result = prime
				* result
				+ ((departureAirportCode == null) ? 0 : departureAirportCode
						.hashCode());
		result = prime * result
				+ ((departureDate == null) ? 0 : departureDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripInformationRequest other = (TripInformationRequest) obj;
		if (arrivalAirportCode == null) {
			if (other.arrivalAirportCode != null)
				return false;
		} else if (!arrivalAirportCode.equals(other.arrivalAirportCode))
			return false;
		if (departureAirportCode == null) {
			if (other.departureAirportCode != null)
				return false;
		} else if (!departureAirportCode.equals(other.departureAirportCode))
			return false;
		if (departureDate == null) {
			if (other.departureDate != null)
				return false;
		} else if (!departureDate.equals(other.departureDate))
			return false;
		return true;
	}
	
	public Timestamp getDepartureDateEndDay() {
		calendar.setTimeInMillis(departureDate.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return new Timestamp(calendar.getTimeInMillis());
	}
	
}
