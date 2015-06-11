package com.nearsoft.flights.interfaces.flexapi.domain.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;

@Component
public class EmptyRequestCallback implements RequestCallback {
	
	@Value("#{mediaType}")
	private List<MediaType> mediaType;

	@Override
	public void doWithRequest(ClientHttpRequest request) throws IOException {
		request.getHeaders().setAccept(this.mediaType);
	}

}
