package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.extractor.ExtractionException;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;

public class AirportExtractorJson implements Extractor<Airport>{

	@Override
	public Airport extract(InputStream in) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		Airport airport = null;
		try {
			AirportPojo airportJson = mapper.readValue(in, AirportPojo.class);
			airport = ExtractorUtils.airportPojoToAirport(airportJson);
		} catch (JsonParseException e) {
			throw new ExtractionException(e);
		} catch (JsonMappingException e) {
			throw new ExtractionException(e);
		} catch (IOException e) {
			throw new ExtractionException(e);
		}
		return airport;
	}
}
