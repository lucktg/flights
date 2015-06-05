package com.nearsoft.flights.domain.model.flight;

public class Airline {
	
	private static final Airline EMPTY_AIRLINE = new Airline();
	
	private String airlineCode;
	private String phoneNumber;
	private String name;
	private String active;
	
	public Airline(String airlineCode, String phoneNumber, String name,
			String active) {
		this.airlineCode = airlineCode;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.active = active;
	}
	
	private Airline() {
		
	}
	
	public String getAirlineCode() {
		return airlineCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getName() {
		return name;
	}
	public String getActive() {
		return active;
	}
	
	public static Airline emptyAirline() {
		return EMPTY_AIRLINE;
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
	
	
	
}
