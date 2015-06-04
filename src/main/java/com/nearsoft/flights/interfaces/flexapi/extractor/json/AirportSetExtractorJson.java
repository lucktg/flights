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

public class AirportSetExtractorJson implements Extractor<Set<Airport>> {

	@Override
	public Set<Airport> extract(InputStream in) throws ExtractionException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		Set<Airport> airports = null;
		try {
			AirportsJson airport = mapper.readValue(in, AirportsJson.class);
			airports = ExtractorUtils.airportJsonSetToAirportSet(airport);
		} catch (JsonParseException e) {
			throw new ExtractionException(e);
		} catch (JsonMappingException e) {
			throw new ExtractionException(e);
		} catch (IOException e) {
			throw new ExtractionException(e);
		}
		return airports;
	}
	
	
	

	static class AirportsJson {
		@JsonProperty("airports")
		private Set<AirportPojo> airports;
		
		//Required by jackson
		AirportsJson() {

		}
		
		public Set<AirportPojo> getAirports() {
			return airports;
		}
	}
}
