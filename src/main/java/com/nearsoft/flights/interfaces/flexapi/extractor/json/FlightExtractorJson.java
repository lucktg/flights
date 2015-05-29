package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
			FlightScheduledPojo pojo = mapper.readValue(in, FlightScheduledPojo.class);
			return ExtractorUtils.flightPojoToFlight(pojo);
		} catch (JsonParseException e) {
			throw new ExtractionException(e);
		} catch (JsonMappingException e) {
			throw new ExtractionException(e);
		} catch (IOException e) {
			throw new ExtractionException(e);
		}
	}

}
