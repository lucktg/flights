package com.nearsoft.flights.interfaces.flexapi;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import jersey.repackaged.com.google.common.collect.Lists;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.AirportAPI;
import com.nearsoft.flights.rest.util.UriUtils;

public class AirportFlexAPI implements AirportAPI {

	private RestTemplate restTemplate;
	
	private Map<String, ?> apiConfig;
	
	public AirportFlexAPI(RestTemplate restTemplate, Map<String, ?> apiConfig) {
		this.restTemplate = restTemplate;
		this.apiConfig = apiConfig;
	}
	
	@Override
	public Set<Airport> getAllActiveAirports() {
		RestExchange<Set<Airport>> restExchange = new RestExchange<Set<Airport>>(restTemplate);
		return restExchange.execute(new JsonExecuteConfig<Set<Airport>>(UriUtils.buildAllActiveAirportsJSONURI(apiConfig),
				HttpMethod.GET, null,
				new GenericResponseExtractor<Set<Airport>>(
						new AirportExtractorFactory())));
	}
	
	
	public void exchangeJSON(){
		restTemplate.execute(UriUtils.buildAllActiveAirportsJSONURI(apiConfig), HttpMethod.GET, 
				new RequestCallback() {

					@Override
					public void doWithRequest(ClientHttpRequest request)
							throws IOException {
						request.getHeaders().setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
					}
			
		}, 
				new ResponseExtractor<Airport>(){

					@Override
					public Airport extractData(ClientHttpResponse response)
							throws IOException {
						AirportExtractorFactory extractor = ExtractorStrategy.getExtractor(response.getHeaders().getContentType());
						return strategy.extract(response.getBody());
					}
			
		});
		
	}
	
	private HttpEntity<Airport> getJSONRequest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		HttpEntity<Airport> request = new HttpEntity<Airport>(null, headers);
		return request;
	}

}
