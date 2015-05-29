package com.nearsoft.flights.interfaces.flexapi;

import java.util.Set;

import org.springframework.http.MediaType;

import com.nearsoft.flights.domain.model.airport.Airport;

public class AirportExtractorFactory implements ExtractorFactory<Set<Airport>>{
	
	public Extractor<Set<Airport>> createExtractor(String mediaType){
		switch(mediaType) {
			case MediaType.APPLICATION_JSON_VALUE :{
				return new AirportExtractorJson();
			}
			default : {
				return null;
			}
		}
	}

}
