package com.nearsoft.flights.domain.model.flight;

import com.nearsoft.flights.domain.model.repository.IgnorePersistence;
import com.nearsoft.flights.domain.model.repository.IgnorePersistence.Operation;
import com.nearsoft.flights.domain.model.repository.Table;

@Table(tableName="airline", idTable="airlineCode")
public class Airline {
	
	@IgnorePersistence(ignore=Operation.ALL)
	private static final Airline EMPTY_AIRLINE = new Airline();
	@IgnorePersistence(ignore=Operation.UPDATE)
	private String airlineCode;
	private String phoneNumber;
	private String airlineName;
	@IgnorePersistence(ignore=Operation.ALL)
	private String active;
	
	public Airline(String airlineCode, String phoneNumber, String airlineName,
			String active) {
		this.airlineCode = airlineCode;
		this.phoneNumber = phoneNumber;
		this.airlineName = airlineName;
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
	public String getAirlineName() {
		return airlineName;
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

	@Override
	public String toString() {
		return new StringBuilder("Airline [airlineCode=")
			.append(airlineCode)
			.append(", phoneNumber=")
			.append(phoneNumber)
			.append(", name=")
			.append(airlineName )
			.append(", active=")
			.append(active)
			.append("]").toString();
	}
	
	
	
}
