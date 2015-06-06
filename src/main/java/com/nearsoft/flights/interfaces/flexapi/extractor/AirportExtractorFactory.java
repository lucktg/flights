package com.nearsoft.flights.interfaces.flexapi.extractor;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.internal.inject.ExtractorException;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.extractor.json.AirportExtractorJson;

public class AirportExtractorFactory implements MediaTypeExtractorFactory<Airport> {

	private static final Logger logger = Logger.getLogger(AirportExtractorFactory.class);
	
	private static final AirportExtractorFactory instance = new AirportExtractorFactory();
	
	public static AirportExtractorFactory getInstance() {
		return instance;
	}

	@Override
	public Extractor<Airport> createExtractor(String mediaType) {
		logger.debug("Creating Airport extractor for mediaType["+mediaType+"]");
		switch(mediaType) {
		case MediaType.APPLICATION_JSON: 
			return new AirportExtractorJson();
		default :
			throw new ExtractorException("No extractor for media type ["+mediaType+"]");
		}
	}
	
	private AirportExtractorFactory( ) {
		
	}

}
