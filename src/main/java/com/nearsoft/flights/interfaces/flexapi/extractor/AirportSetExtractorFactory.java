package com.nearsoft.flights.interfaces.flexapi.extractor;

import java.util.Set;

import org.glassfish.jersey.internal.inject.ExtractorException;
import org.springframework.http.MediaType;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.extractor.json.AirportSetExtractorJson;

public class AirportSetExtractorFactory implements MediaTypeExtractorFactory<Set<Airport>>{
	
	public Extractor<Set<Airport>> createExtractor(String mediaType){
		switch(mediaType) {
			case MediaType.APPLICATION_JSON_VALUE :{
				return new AirportSetExtractorJson();
			}
			default : {
				throw new ExtractorException("No extractor for media type ["+mediaType+"]");
			}
		}
	}

}
