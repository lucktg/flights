package com.nearsoft.flights.rest.util;

import java.net.URI;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

public class UriUtils {	
	
	private static final String mainPath= "https://api.flightstats.com";
	private static final String ACTIVE_AIRPORTS_JSON = "flex/airports/rest/v1/json/active";
	private static final String SCHEDULED_FLIGHTS_BY_ROUTE_N_DATE_JSON = "flex/schedules/rest/v1/json/from/{departureAirportCode}/to/{arrivalAirportCode}/departing/{year}/{month}/{day}";
	private static final String AIRPORT_BY_CODE = "flex/airports/rest/v1/json/{code}/today";
	public static URI buildAllActiveAirportsJSONURI(Map<String,?> queryParams) {
		return buildUri(queryParams, null, ACTIVE_AIRPORTS_JSON);
	}
	
	public static URI buildAirportsJSONURI(Map<String,?> queryParams, Map<String,?> urlParams) {
		return buildUri(queryParams, urlParams, AIRPORT_BY_CODE);
	}
	
	public static URI buildScheduledDepartingFlightsByRouteNDateJSON(Map<String,?> queryParams, Map<String,?> urlParams){
		return buildUri(queryParams, urlParams, SCHEDULED_FLIGHTS_BY_ROUTE_N_DATE_JSON);
	}
	
	private static URI buildUri(Map<String,?> queryParams, Map<String,?> urlParams, String path ) {
		UriBuilder builder = UriBuilder.fromPath(mainPath);
		builder.path(path);
		if ( queryParams != null) {
			for (String key : queryParams.keySet()) {
				builder.queryParam(key, queryParams.get(key));
			}
		}
		return urlParams != null ? builder.buildFromMap(urlParams ) : builder.build();
	}
	
	private UriUtils() {
		throw new AssertionError("No instance of this class");
	}
}