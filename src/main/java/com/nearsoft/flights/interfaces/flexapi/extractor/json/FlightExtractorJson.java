package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.interfaces.flexapi.extractor.ExtractionException;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;

public class FlightExtractorJson implements Extractor<Flight>{

	@Override
	public Flight extract(InputStream in) throws ExtractionException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		try {
			FlightScheduledPojoWrapper pojo = mapper.readValue(in, FlightScheduledPojoWrapper.class);
			Map<String, AirlinePojo> airlinesMap = pojo != null && pojo.appendix != null ? 
					pojo.appendix.getAirlines().stream().collect(Collectors.toMap(AirlinePojo::getFs, p -> p)):Collections.emptyMap();;
			Map<String, AirportPojo> airportsMap = pojo != null && pojo.appendix != null ? 
					pojo.appendix.getAirports().stream().collect(Collectors.toMap(AirportPojo::getFs, p -> p)):Collections.emptyMap();
			return ExtractorUtils.flightPojoToFlight(pojo != null ? pojo.flightPojo : new FlightScheduledPojo(), 
					airlinesMap, airportsMap);
		} catch (JsonParseException e) {
			throw new ExtractionException(e);
		} catch (JsonMappingException e) {
			throw new ExtractionException(e);
		} catch (IOException e) {
			throw new ExtractionException(e);
		}
	}
	
	static class FlightScheduledPojoWrapper {
		
		@JsonProperty("flightScheduled")
		FlightScheduledPojo flightPojo;
		
		@JsonProperty("appendix")
		AppendixPojo appendix;
		
		public FlightScheduledPojoWrapper() {
			
		}
	}

}
