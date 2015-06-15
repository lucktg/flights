package com.nearsoft.flights.interfaces.flexapi.domain.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;
import com.nearsoft.flights.interfaces.FlightApiService;

@Service
public class FlightFlexApiServiceImpl  implements FlightApiService {
	enum URLParams {
		departureAirportCode,
		arrivalAirportCode,
		year,
		month,
		day
	}
	
	
	@Value("#{apiConfig}")
	private Map<String, String> apiConfig;
	
	@Autowired
	private RestServiceInvoker restServiceInvoker;
	
	@Override
	public Set<Flight> getDepartingFlightsByTripInformation(TripInformationRequest infoRequest) {
		Map<String,String> urlParams = urlParamsFromTripInformation(infoRequest.getDepartureAirportCode(), 
				infoRequest.getArrivalAirportCode(), 
				infoRequest.getDepartureDate());
		return restServiceInvoker.invoke(UriUtils.buildScheduledDepartingFlightsByRouteNDateJSON(apiConfig, urlParams), 
				ExtractorJsonUtils::flightJsonSetToFlightSet, 
				FlightJsonSetWrapper.class);
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
