package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.interfaces.flexapi.extractor.ExtractionException;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;

public class FlightSetExtractorJson implements Extractor<Set<Flight>> {

	@Override
	public Set<Flight> extract(InputStream in) throws ExtractionException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		try {
			FlightsJson pojo = mapper.readValue(in, FlightsJson.class);
			return ExtractorUtils.flightJsonSetToFlightSet(pojo);
		} catch (JsonParseException e) {
			throw new ExtractionException(e);
		} catch (JsonMappingException e) {
			throw new ExtractionException(e);
		} catch (IOException e) {
			throw new ExtractionException(e);
		}
	}
@JsonIgnoreProperties(ignoreUnknown = true)
 static class FlightsJson {
	 
	 @JsonProperty("scheduledFlights")
	 Set<FlightScheduledPojo> flights;
	 @JsonProperty("appendix")
	 AppendixPojo appendix;
	 
	 public FlightsJson(){
		 
	 }
	 
	 public Set<FlightScheduledPojo> getFlights() {
		return flights;
	}
	 
	 public AppendixPojo getAppendix() {
		return appendix;
	}
 }
}
