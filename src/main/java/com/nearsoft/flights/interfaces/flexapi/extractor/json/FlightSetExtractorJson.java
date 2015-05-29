package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.io.InputStream;
import java.util.Set;

import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.interfaces.flexapi.extractor.ExtractionException;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;

public class FlightSetExtractorJson implements Extractor<Set<Flight>> {

	@Override
	public Set<Flight> extract(InputStream in) throws ExtractionException {
		// TODO Auto-generated method stub
		return null;
	}

}
