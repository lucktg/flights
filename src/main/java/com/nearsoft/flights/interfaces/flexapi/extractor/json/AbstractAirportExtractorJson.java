package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;

public abstract class AbstractAirportExtractorJson<T> implements Extractor<T> {

		

	Airport airportJsonToAirport(AirportJson airportJson){
		AirportBuilder airportBuilder = new AirportBuilder(airportJson.fs);
		airportBuilder.addCity(airportJson.city);
		airportBuilder.addCityCode(airportJson.cityCode);
		airportBuilder.addCountryCode(airportJson.countryCode);
		airportBuilder.addCountryName(airportJson.countryName);
		airportBuilder.addLatitude(airportJson.latitude);
		airportBuilder.addLongitude(airportJson.longitude);
		airportBuilder.addName(airportJson.name);
		airportBuilder.addStateCode(airportJson.stateCode);
		return airportBuilder.build();
	}
	
	static class AirportJson {
		
		//Required by jackson
		public AirportJson() {
			
		}
		
		private String fs;
		private String iata;
		private String icao;
		private String faa;
		private String name;
		private String street1;
		private String street2;
		private String city;
		private String cityCode;
		private String stateCode;
		private String postalCode;
		private String countryCode;
		private String countryName;
		private String regionName;
		private String timeZoneRegionName;
		private String weatherZone;
		private String localTime;
		private String utcOffsetHours;
		private String latitude;
		private String longitude;
		private String elevationFeet;
		private String classification;
		private String active;
		private String delayIndexUrl;
		private String weatherUrl;
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((fs == null) ? 0 : fs.hashCode());
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
			AirportJson other = (AirportJson) obj;
			if (fs == null) {
				if (other.fs != null)
					return false;
			} else if (!fs.equals(other.fs))
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
 }
}
