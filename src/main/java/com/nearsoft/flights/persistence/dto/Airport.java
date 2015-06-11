package com.nearsoft.flights.persistence.dto;

import java.io.Serializable;

@Deprecated
public class Airport implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7696829063276592732L;
	
	private String airportCode;
	private String airportName;
	private String city;
	private String cityCode;
	private String countryCode;
	private String countryName;
	private String latitude;
	private String longitude;
	private String stateCode;
	
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getAirportName() {
		return airportName;
	}
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((airportCode == null) ? 0 : airportCode.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
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
		Airport other = (Airport) obj;
		if (airportCode == null) {
			if (other.airportCode != null)
				return false;
		} else if (!airportCode.equals(other.airportCode))
			return false;
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
		return true;
	}
	@Override
	public String toString() {
		return new StringBuilder("AirportDto [airportCode=")
			.append(airportCode)
			.append(", airportName=")
			.append(airportName)
			.append(", city=")
			.append(city)
			.append(", cityCode=")
			.append(cityCode)
			.append(", countryCode=")
			.append(countryCode)
			.append(", countryName=")
			.append(countryName)
			.append(", latitude=")
			.append(latitude)
			.append(", longitude=")
			.append(longitude)
			.append("]").toString();
	}
}
