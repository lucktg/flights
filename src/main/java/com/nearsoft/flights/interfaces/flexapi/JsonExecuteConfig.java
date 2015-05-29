package com.nearsoft.flights.interfaces.flexapi;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

public class JsonExecuteConfig<T> implements ExchangeConfig<T> {
	
	private static final String JSON_MEDIA_TYPE = MediaType.APPLICATION_JSON;
	
	private URI uri;
	private HttpMethod httpMethod;
	private RequestCallback requestCallback;
	private ResponseExtractor<T> responseExtractor;
	
	
	public JsonExecuteConfig(URI uri, HttpMethod httpMethod,
			RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) {
		this.uri = uri;
		this.httpMethod = httpMethod;
		this.requestCallback = requestCallback;
		this.responseExtractor = responseExtractor;
	}

	public URI getURI() {
		return uri;
	}

	public HttpMethod getMethod() {
		return httpMethod;
	}

	public RequestCallback getRequestCallback() {
		return requestCallback;
	}

	public ResponseExtractor<T> getResponseExtractor() {
		return responseExtractor;
	}

}
