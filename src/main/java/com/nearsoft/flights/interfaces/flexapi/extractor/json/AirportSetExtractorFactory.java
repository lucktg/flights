package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.util.Set;

import org.springframework.http.MediaType;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;
import com.nearsoft.flights.interfaces.flexapi.extractor.MediaTypeExtractorFactory;

public class AirportSetExtractorFactory implements MediaTypeExtractorFactory<Set<Airport>>{
	
	public Extractor<Set<Airport>> createExtractor(String mediaType){
		switch(mediaType) {
			case MediaType.APPLICATION_JSON_VALUE :{
				return new AirportSetExtractorJson();
			}
			default : {
				return null;
			}
		}
	}

}
