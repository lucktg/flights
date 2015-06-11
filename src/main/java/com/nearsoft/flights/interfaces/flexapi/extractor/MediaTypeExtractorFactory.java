package com.nearsoft.flights.interfaces.flexapi.extractor;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.internal.inject.ExtractorException;

import com.nearsoft.flights.interfaces.flexapi.extractor.json.JsonExtractor;

public class MediaTypeExtractorFactory  {

	private static final Logger logger = Logger.getLogger(MediaTypeExtractorFactory.class);
	
	private static final MediaTypeExtractorFactory instance = new MediaTypeExtractorFactory();
	
	public static MediaTypeExtractorFactory getInstance() {
		return instance;
	}

	public Extractor createExtractor(String mediaType) {
		logger.debug("Creating Airport extractor for mediaType["+mediaType+"]");
		switch(mediaType) {
		case MediaType.APPLICATION_JSON: 
			return new JsonExtractor();
		default :
			throw new ExtractorException("No extractor for media type ["+mediaType+"]");
		}
	}
	
	private MediaTypeExtractorFactory( ) {
		
	}

}
