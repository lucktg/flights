package com.nearsoft.flights.persistence.dto;

import java.io.Serializable;
@Deprecated
public class Airline implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4959164403326381679L;
	
	private String airlineCode;
	private String airlineName;
	private String phoneNumber;
	private String active;
	
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((airlineCode == null) ? 0 : airlineCode.hashCode());
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
		Airline other = (Airline) obj;
		if (airlineCode == null) {
			if (other.airlineCode != null)
				return false;
		} else if (!airlineCode.equals(other.airlineCode))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("AirlineDto [airlineCode=")
			.append(airlineCode)
			.append(", airlineName=")
			.append(airlineName)
			.append(", phoneNumber=")
			.append(phoneNumber)
			.append("]").toString();
	}
	
	
	

}
