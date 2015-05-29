package com.nearsoft.flights.interfaces.flexapi.extractor.json;

public class AirlinePojo {
	private String fs;
	private String iata;
	private String icao;
	private String shortName;
	private String name;
	private String phoneNumber;
	private String active;
	
	public AirlinePojo() {}

	protected String getFs() {
		return fs;
	}

	protected String getIata() {
		return iata;
	}

	protected String getIcao() {
		return icao;
	}

	protected String getName() {
		return name;
	}

	protected String getActive() {
		return active;
	}
	
	protected String getShortName() {
		return shortName;
	}

	protected String getPhoneNumber() {
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
