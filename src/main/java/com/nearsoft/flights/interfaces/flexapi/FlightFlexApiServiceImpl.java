package com.nearsoft.flights.interfaces.flexapi;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;
import com.nearsoft.flights.interfaces.FlightFlexApiService;
import com.nearsoft.flights.interfaces.flexapi.extractor.FlightSetExtractorFactory;
import com.nearsoft.flights.interfaces.flexapi.extractor.MediaTypeResponseExtractor;
import com.nearsoft.flights.rest.util.UriUtils;

@Service
public class FlightFlexApiServiceImpl implements FlightFlexApiService {
	enum URLParams {
		departureAirportCode,
		arrivalAirportCode,
		year,
		month,
		day
	}
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Map<String, String> apiConfig;
	@Autowired
	private RequestCallback requestCallback;

	@Override
	public Set<Flight> getDepartingFlightsByTripInformation(TripInformationRequest infoRequest) {
		Map<String,String> urlParams = urlParamsFromTripInformation(infoRequest.getDepartureAirportCode(), 
				infoRequest.getArrivalAirportCode(), 
				infoRequest.getDepartureDate());
		return restTemplate.execute(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(apiConfig, urlParams), 
				HttpMethod.GET, 
				requestCallback, 
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
