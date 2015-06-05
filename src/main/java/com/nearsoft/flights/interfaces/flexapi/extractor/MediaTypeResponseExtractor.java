package com.nearsoft.flights.interfaces.flexapi.extractor;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

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
		MediaType mediaType = new MediaType(response.getHeaders().getContentType().getType(), response.getHeaders().getContentType().getSubtype());
		Extractor<T> extractor = extractorFactory.createExtractor(mediaType.toString());
		return extractor.extract(response.getBody());
	}

}
