package com.nearsoft.flights.vo;

import javax.annotation.security.DenyAll;

@Deprecated
public class AirportDTO implements FlightItem {
	
	private String name;
	private String city;
	private String cityCode;
	private String stateCode;
	private String countryCode;
	private String countryName;
	private String latitude;
	private String longitude;
	
	public AirportDTO() {
		
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Airport [");
		builder.append("name=").append(name).append(",")
		.append("city=").append(city).append(",")
		.append("cityCode=").append(cityCode).append(",")
		.append("stateCode=").append(stateCode).append(",")
		.append("countryCode=").append(countryCode).append(",")
		.append("countryName=").append(countryName).append(",")
		.append("latitude=").append(latitude).append(",")
		.append("longitude=").append(longitude);
		
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AirportDTO other = (AirportDTO) obj;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
