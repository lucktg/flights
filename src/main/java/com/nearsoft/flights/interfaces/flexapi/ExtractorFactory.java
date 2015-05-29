package com.nearsoft.flights.interfaces.flexapi;

public interface ExtractorFactory<T> {
	Extractor<T> createExtractor(String mediaType);
}
