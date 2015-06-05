package com.nearsoft.flights.interfaces.flexapi.extractor;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.internal.inject.ExtractorException;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.extractor.json.AirportExtractorJson;

public class AirportExtractorFactory implements MediaTypeExtractorFactory<Airport>{

	@Override
	public Extractor<Airport> createExtractor(String mediaType) {
		switch(mediaType) {
		case MediaType.APPLICATION_JSON: 
			return new AirportExtractorJson();
		default :
			throw new ExtractorException("No extractor for media type ["+mediaType+"]");
		}
	}

}
