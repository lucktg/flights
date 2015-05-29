package com.nearsoft.flights.interfaces.flexapi.extractor.json;

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
import com.nearsoft.flights.interfaces.flexapi.extractor.ExtractionException;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;

public class AirportSetExtractorJson extends AbstractAirportExtractorJson<Set<Airport>> {

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
	

	static class AirportsJson {
		@JsonProperty("airports")
		private Set<AirportJson> airports;
		
		//Required by jackson
		AirportsJson() {

		}
		
		public Set<AirportJson> getAirports() {
			return airports;
		}
	}
		
	public static void main(String[] args) throws Exception {
		URL url = new URL("https://api.flightstats.com/flex/airports/samples/v1/lts/Airports_response.json");
		Extractor<Set<Airport>> extractor = new AirportSetExtractorJson();
		System.out.println(extractor.extract(url.openStream()));;
		
	}
}
