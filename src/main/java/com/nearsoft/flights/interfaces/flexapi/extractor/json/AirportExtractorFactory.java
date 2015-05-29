package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import javax.ws.rs.core.MediaType;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;
import com.nearsoft.flights.interfaces.flexapi.extractor.MediaTypeExtractorFactory;

public class AirportExtractorFactory implements MediaTypeExtractorFactory<Airport>{

	@Override
	public Extractor<Airport> createExtractor(String mediaType) {
		switch(mediaType) {
		case MediaType.APPLICATION_JSON: 
			return new AirportExtractorJson();
		default :
			return null;
		}
	}

}
