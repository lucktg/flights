package com.nearsoft.flights.interfaces.flexapi.extractor;

public interface MediaTypeExtractorFactory<T> {
	Extractor<T> createExtractor(String mediaType);
}
