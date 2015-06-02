package com.nearsoft.flights.persistence.dto;

import java.io.Serializable;

public class AirlineDto implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4959164403326381679L;
	
	private String airlineCode;
	private String airlineName;
	private String phoneNumber;
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
		AirlineDto other = (AirlineDto) obj;
		if (airlineCode == null) {
			if (other.airlineCode != null)
				return false;
		} else if (!airlineCode.equals(other.airlineCode))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AirlineDto [airlineCode=" + airlineCode + ", airlineName="
				+ airlineName + ", phoneNumber=" + phoneNumber + "]";
	}
	
	

}
