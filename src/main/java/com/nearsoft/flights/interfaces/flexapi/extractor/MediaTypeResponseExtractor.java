package com.nearsoft.flights.interfaces.flexapi.extractor;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

public class MediaTypeResponseExtractor<T> implements ResponseExtractor<T> {
	
	private MediaTypeExtractorFactory<T> extractorFactory;
	
	public MediaTypeResponseExtractor(MediaTypeExtractorFactory<T> extractorFactory) {
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
