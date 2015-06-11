package com.nearsoft.flights.interfaces.flexapi.domain.model;

public class AirlinePojo {
	private String fs;
	private String iata;
	private String icao;
	private String shortName;
	private String name;
	private String phoneNumber;
	private String active;
	
	public AirlinePojo() {}

	public String getFs() {
		return fs;
	}

	public String getIata() {
		return iata;
	}

	public String getIcao() {
		return icao;
	}

	public String getName() {
		return name;
	}

	public String getActive() {
		return active;
	}
	
	public String getShortName() {
		return shortName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fs == null) ? 0 : fs.hashCode());
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
		AirlinePojo other = (AirlinePojo) obj;
		if (fs == null) {
			if (other.fs != null)
				return false;
		} else if (!fs.equals(other.fs))
			return false;
		return true;
	}
}
