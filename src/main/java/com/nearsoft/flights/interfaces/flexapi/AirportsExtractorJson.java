package com.nearsoft.flights.interfaces.flexapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;

public class AirportsExtractorJson implements Extractor<Set<Airport>> {

	@Override
	public Set<Airport> extract(InputStream in) throws ExtractionException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		Set<Airport> airports = Collections.emptySet();
		try {
			AirportsJson airport = mapper.readValue(in, AirportsJson.class);
			airports = airportJsonSetToAirportSet(airport);
		} catch (JsonParseException e) {
			throw new ExtractionException(e);
		} catch (JsonMappingException e) {
			throw new ExtractionException(e);
		} catch (IOException e) {
			throw new ExtractionException(e);
		}
		return airports;
	}
	
	private Set<Airport> airportJsonSetToAirportSet(AirportsJson airports){
		return airports.getAirports().stream().map(airport -> airportJsonToAirport(airport)).collect(Collectors.toSet());
	}
	
	private Airport airportJsonToAirport(AirportJson airportJson){
		AirportBuilder airportBuilder = new AirportBuilder(airportJson.fs, 
				airportJson.latitude, 
				airportJson.longitude);
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

	private static class AirportsJson {
		@JsonProperty("airports")
		private Set<AirportJson> airports;
		
		//Required by jackson
		AirportsJson() {

		}
		
		public Set<AirportJson> getAirports() {
			return airports;
		}
	}
	
	private static class AirportJson {
		
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
	
	public static void main(String[] args) throws Exception {
		URL url = new URL("https://api.flightstats.com/flex/airports/samples/v1/lts/Airports_response.json");
		Extractor<Set<Airport>> extractor = new AirportsExtractorJson();
		System.out.println(extractor.extract(url.openStream()));;
		
	}
}
