package com.nearsoft.flights.interfaces.flexapi.extractor;

import java.util.Set;

import javax.ws.rs.core.MediaType;

import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.interfaces.flexapi.extractor.json.FlightSetExtractorJson;

public class FlightSetExtractorFactory implements
		MediaTypeExtractorFactory<Set<Flight>> {

	@Override
	public Extractor<Set<Flight>> createExtractor(String mediaType) {
		switch(mediaType){
		case MediaType.APPLICATION_JSON :
			return new FlightSetExtractorJson();
		default :
			return null;
		}
	}

}
