package com.nearsoft.flights.interfaces.flexapi.extractor.json;


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
		protected String getFs() {
			return fs;
		}
		protected String getIata() {
			return iata;
		}
		protected String getIcao() {
			return icao;
		}
		protected String getFaa() {
			return faa;
		}
		protected String getName() {
			return name;
		}
		protected String getStreet1() {
			return street1;
		}
		protected String getStreet2() {
			return street2;
		}
		protected String getCity() {
			return city;
		}
		protected String getCityCode() {
			return cityCode;
		}
		protected String getStateCode() {
			return stateCode;
		}
		protected String getPostalCode() {
			return postalCode;
		}
		protected String getCountryCode() {
			return countryCode;
		}
		protected String getCountryName() {
			return countryName;
		}
		protected String getRegionName() {
			return regionName;
		}
		protected String getTimeZoneRegionName() {
			return timeZoneRegionName;
		}
		protected String getWeatherZone() {
			return weatherZone;
		}
		protected String getLocalTime() {
			return localTime;
		}
		protected String getUtcOffsetHours() {
			return utcOffsetHours;
		}
		protected String getLatitude() {
			return latitude;
		}
		protected String getLongitude() {
			return longitude;
		}
		protected String getElevationFeet() {
			return elevationFeet;
		}
		protected String getClassification() {
			return classification;
		}
		protected String getActive() {
			return active;
		}
		protected String getDelayIndexUrl() {
			return delayIndexUrl;
		}
		protected String getWeatherUrl() {
			return weatherUrl;
		}
 }