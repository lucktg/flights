package com.nearsoft.flights.interfaces.flexapi;

import org.springframework.web.client.RestTemplate;

public class RestExchange<T> {
	
	private RestTemplate restTemplate;
	
	public RestExchange(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	
	public T execute(ExchangeConfig<T> executeConfig) {
		return restTemplate.execute(executeConfig.getURI(), 
				executeConfig.getMethod(), 
				executeConfig.getRequestCallback(), 
				executeConfig.getResponseExtractor());
	}
}
