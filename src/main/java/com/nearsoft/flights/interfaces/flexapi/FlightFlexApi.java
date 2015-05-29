package com.nearsoft.flights.interfaces.flexapi;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.interfaces.FlightApi;
import com.nearsoft.flights.interfaces.flexapi.extractor.MediaTypeResponseExtractor;
import com.nearsoft.flights.rest.util.UriUtils;
import com.nearsoft.flights.vo.TripInformationRequest;

public class FlightFlexApi implements FlightApi {
	enum URLParams {
		departureAirportCode,
		arrivalAirportCode,
		year,
		month,
		day
	}
	
	private RestTemplate restTemplate;
	private Map<String, String> apiConfig;


	public FlightFlexApi(RestTemplate restTemplate,
			Map<String, String> apiConfig) {
		this.restTemplate = restTemplate;
		this.apiConfig = apiConfig;
	}

	@Override
	public Set<Flight> getDepartingFlightsByTripInformation(TripInformationRequest infoRequest) {
		Map<String,String> urlParams = urlParamsFromTripInformation(infoRequest.getDepartureAirportCode(), 
				infoRequest.getArrivalAirportCode(), 
				infoRequest.getDepartureDate());
		return restTemplate.execute(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(apiConfig, urlParams), 
				HttpMethod.GET, 
				new EmptyRequestCallback(Collections.singletonList(MediaType.APPLICATION_JSON)), 
				new MediaTypeResponseExtractor<Set<Flight>>(new FlightSetExtractorFactory()));
	}
	
	private Map<String, String> urlParamsFromTripInformation(String departureAirportCode, 
			String arrivalAirportCode, 
			Date departureDate){
		Map<String,String> urlParams = new HashMap<String,String>();
		urlParams.put(URLParams.departureAirportCode.toString(), departureAirportCode);
		urlParams.put(URLParams.arrivalAirportCode.toString(), arrivalAirportCode);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(departureDate);
		urlParams.put(URLParams.year.toString(), Integer.toString(calendar.get(Calendar.YEAR)));
		urlParams.put(URLParams.month.toString(), Integer.toString(calendar.get(Calendar.MONTH)+1));
		urlParams.put(URLParams.day.toString(), Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
		return urlParams;
	}

}
