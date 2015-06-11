package com.nearsoft.flights.interfaces.flexapi.domain.services;

import java.net.URI;
import java.util.function.Function;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;
import com.nearsoft.flights.interfaces.flexapi.extractor.MediaTypeExtractorFactory;

public class RestServiceInvoker {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RequestCallback requestCallback;
	
	private MediaTypeExtractorFactory extractorFactory = MediaTypeExtractorFactory.getInstance();
	
	public <K,T> T invoke(URI uri, Function<K,T> function, Class<K> clazz){
		return restTemplate.execute(uri,
				HttpMethod.GET, 
				requestCallback, response -> {
					MediaType mediaType = new MediaType(response.getHeaders().getContentType().getType(), response.getHeaders().getContentType().getSubtype());
					Extractor extractor = extractorFactory.createExtractor(mediaType.toString());
					return extractor.extract(response.getBody(), function, clazz);
				});
	}
}
