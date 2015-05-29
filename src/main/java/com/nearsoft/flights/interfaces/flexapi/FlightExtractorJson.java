package com.nearsoft.flights.interfaces.flexapi;

import java.io.InputStream;
import java.util.Set;

import com.nearsoft.flights.domain.model.flight.Flight;

public class FlightExtractorJson implements Extractor<Set<Flight>> {

	@Override
	public Set<Flight> extract(InputStream in) throws ExtractionException {
		// TODO Auto-generated method stub
		return null;
	}

}
