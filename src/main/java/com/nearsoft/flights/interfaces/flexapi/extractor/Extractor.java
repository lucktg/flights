package com.nearsoft.flights.interfaces.flexapi.extractor;

import java.io.InputStream;

public interface Extractor <T> {
	public T extract(InputStream in) throws ExtractionException;
}
