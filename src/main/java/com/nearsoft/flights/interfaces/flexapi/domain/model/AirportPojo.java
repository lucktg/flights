package com.nearsoft.flights.interfaces.flexapi.domain.model;


public class AirportPojo {
		
		//Required by jackson
		public AirportPojo() {
			
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
			AirportPojo other = (AirportPojo) obj;
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
		public String getFs() {
			return fs;
		}
		public String getIata() {
			return iata;
		}
		public String getIcao() {
			return icao;
		}
		public String getFaa() {
			return faa;
		}
		public String getName() {
			return name;
		}
		public String getStreet1() {
			return street1;
		}
		public String getStreet2() {
			return street2;
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
		public String getPostalCode() {
			return postalCode;
		}
		public String getCountryCode() {
			return countryCode;
		}
		public String getCountryName() {
			return countryName;
		}
		public String getRegionName() {
			return regionName;
		}
		public String getTimeZoneRegionName() {
			return timeZoneRegionName;
		}
		public String getWeatherZone() {
			return weatherZone;
		}
		public String getLocalTime() {
			return localTime;
		}
		public String getUtcOffsetHours() {
			return utcOffsetHours;
		}
		public String getLatitude() {
			return latitude;
		}
		public String getLongitude() {
			return longitude;
		}
		public String getElevationFeet() {
			return elevationFeet;
		}
		public String getClassification() {
			return classification;
		}
		public String getActive() {
			return active;
		}
		public String getDelayIndexUrl() {
			return delayIndexUrl;
		}
		public String getWeatherUrl() {
			return weatherUrl;
		}
		@Override
		public String toString() {
			return "AirportPojo [fs=" + fs + ", iata=" + iata + ", icao="
					+ icao + ", faa=" + faa + ", name=" + name + ", street1="
					+ street1 + ", street2=" + street2 + ", city=" + city
					+ ", cityCode=" + cityCode + ", stateCode=" + stateCode
					+ ", postalCode=" + postalCode + ", countryCode="
					+ countryCode + ", countryName=" + countryName
					+ ", regionName=" + regionName + ", timeZoneRegionName="
					+ timeZoneRegionName + ", weatherZone=" + weatherZone
					+ ", localTime=" + localTime + ", utcOffsetHours="
					+ utcOffsetHours + ", latitude=" + latitude
					+ ", longitude=" + longitude + ", elevationFeet="
					+ elevationFeet + ", classification=" + classification
					+ ", active=" + active + ", delayIndexUrl=" + delayIndexUrl
					+ ", weatherUrl=" + weatherUrl + "]";
		}
		
		
 }