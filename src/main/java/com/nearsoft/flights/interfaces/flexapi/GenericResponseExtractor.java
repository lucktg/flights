package com.nearsoft.flights.interfaces.flexapi;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

public class GenericResponseExtractor<T> implements ResponseExtractor<T> {
	
	private ExtractorFactory<T> extractorFactory;
	
	public GenericResponseExtractor(ExtractorFactory<T> extractorFactory) {
		this.extractorFactory = extractorFactory;
	}
	
	@Override
	public T extractData(ClientHttpResponse response)
			throws IOException {
		Extractor<T> extractor = extractorFactory.createExtractor(response.getHeaders().getContentType().toString());
		try {
			return extractor.extract(response.getBody());
		} catch (ExtractionException e) {
			throw new RuntimeException(e);
		}
	}

}
