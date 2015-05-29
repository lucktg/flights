package com.nearsoft.flights.interfaces.flexapi;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

public interface ExchangeConfig<T> {
	
	URI getURI();

	HttpMethod getMethod();

	RequestCallback getRequestCallback();

	ResponseExtractor<T> getResponseExtractor();
}
