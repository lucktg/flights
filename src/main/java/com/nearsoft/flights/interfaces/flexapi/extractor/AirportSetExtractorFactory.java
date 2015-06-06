package com.nearsoft.flights.interfaces.flexapi.extractor;

import java.util.Set;

import org.apache.log4j.Logger;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.springframework.http.MediaType;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.extractor.json.AirportSetExtractorJson;

public class AirportSetExtractorFactory implements MediaTypeExtractorFactory<Set<Airport>>{
	
	private static final Logger logger = Logger.getLogger(AirportSetExtractorFactory.class);
	
	private static final AirportSetExtractorFactory instance = new AirportSetExtractorFactory();
	
	public static AirportSetExtractorFactory getInstance() {
		return instance;
	}
	
	public Extractor<Set<Airport>> createExtractor(String mediaType){
		logger.debug("Creating extractor for mediaType["+mediaType+"]");
		switch(mediaType) {
			case MediaType.APPLICATION_JSON_VALUE :{
				return new AirportSetExtractorJson();
			}
			default : {
				throw new ExtractorException("No extractor for media type ["+mediaType+"]");
			}
		}
	}

	private AirportSetExtractorFactory() {
		
	}
}
