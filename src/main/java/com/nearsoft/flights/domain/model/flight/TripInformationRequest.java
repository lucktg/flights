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
	
	
	
}
