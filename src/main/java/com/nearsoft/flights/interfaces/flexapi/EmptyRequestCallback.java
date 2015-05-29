package com.nearsoft.flights.interfaces.flexapi;

import java.io.IOException;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

public class EmptyRequestCallback implements RequestCallback {
	
	private List<MediaType> mediaType;
	
	public EmptyRequestCallback(List<MediaType> mediaType) {
		this.mediaType = mediaType;
	}

	@Override
	public void doWithRequest(ClientHttpRequest request) throws IOException {
		request.getHeaders().setAccept(this.mediaType);
	}

}
