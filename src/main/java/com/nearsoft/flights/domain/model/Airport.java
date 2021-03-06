package com.nearsoft.flights.domain.model;

import com.nearsoft.flights.domain.repository.jdbc.annotation.IgnorePersistence;
import com.nearsoft.flights.domain.repository.jdbc.annotation.Table;
import com.nearsoft.flights.domain.repository.jdbc.annotation.IgnorePersistence.Operation;

@Table(tableName="airport", idTable = "airportCode")
public class Airport {
	
	@IgnorePersistence(ignore=Operation.ALL)
	private static Airport EMPTY_AIRPORT = new Airport();
	@IgnorePersistence(ignore=Operation.UPDATE)
	private String airportCode;
	private String airportName;
	private String city;
	private String cityCode;
	@IgnorePersistence(ignore=Operation.ALL)
	private String stateCode;
	private String countryCode;
	private String countryName;
	private String latitude;
	private String longitude;
	
	private Airport() {
		
	}
	
	public static Airport emptyAirport() {
		return EMPTY_AIRPORT;
	}
	
	private Airport(String airportCode) {
		this.airportCode = airportCode;
	}
	
	
	public String getAirportCode() {
		return airportCode;
	}

	public String getAirportName() {
		return airportName;
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
		builder.append("airportCode=").append(airportCode).append(",")
			.append("name=").append(airportName).append(",")
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


	
	public static class AirportBuilder {
		private String airportCode = "";
		private String airportName = "";
		private String city = "";
		private String cityCode = "";
		private String stateCode = "";
		private String countryCode = "";
		private String countryName = "";
		private String latitude = "";
		private String longitude = "";

		public AirportBuilder (String airportCode) {
			this.airportCode = airportCode;
		}
		
		public AirportBuilder addAirportName(String airportName){
			this.airportName = airportName;
			return this;
		}
		
		public AirportBuilder addCity(String city){
			this.city = city;
			return this;
		}
		
		public AirportBuilder addCityCode(String cityCode){
			this.cityCode= cityCode;
			return this;
		}
		
		public AirportBuilder addStateCode(String stateCode) {
			this.stateCode = stateCode;
			return this;
		}
		
		public AirportBuilder addCountryCode(String countryCode) {
			this.countryCode = countryCode;
			return this;
		}
		
		public AirportBuilder addCountryName(String countryName) {
			this.countryName = countryName;
			return this;
		}
		
		public AirportBuilder addLatitude(String latitude) {
			this.latitude = latitude;
			return this;
		}
		
		public AirportBuilder addLongitude(String longitude) {
			this.longitude = longitude;
			return this;
		}
		
		public Airport build() {
			Airport airport = new Airport(this.airportCode);
			airport.latitude = this.latitude;
			airport.longitude = this.longitude;
			airport.city = this.city;
			airport.cityCode = this.cityCode;
			airport.countryCode = this.countryCode;
			airport.countryName = this.countryName;
			airport.latitude = this.latitude;
			airport.longitude = this.longitude;
			airport.airportName = this.airportName;
			airport.stateCode = this.stateCode;
			return airport;
		}
	}
}
