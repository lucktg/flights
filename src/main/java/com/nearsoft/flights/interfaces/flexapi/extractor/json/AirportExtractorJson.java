package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.extractor.ExtractionException;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;

public class AirportExtractorJson implements Extractor<Airport>{

	private static final Logger logger = Logger
			.getLogger(AirportExtractorJson.class);
	
	@Override
	public Airport extract(InputStream in) {
		logger.debug("Extracting Airport from JSON response");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		Airport airport = null;
		try {
			AirportPojo airportJson = mapper.readValue(in, AirportPojo.class);
			airport = ExtractorUtils.airportPojoToAirport(airportJson);
		} catch (JsonParseException e) {
			logger.error(e);
			throw new ExtractionException(e);
		} catch (JsonMappingException e) {
			logger.error(e);
			throw new ExtractionException(e);
		} catch (IOException e) {
			logger.error(e);
			throw new ExtractionException(e);
		}
		return airport;
	}
}
