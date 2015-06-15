package com.nearsoft.flights.rest;

import org.glassfish.jersey.server.ResourceConfig;

import com.nearsoft.flights.rest.exception.mapper.ExtractionExceptionMapper;
import com.nearsoft.flights.rest.resources.AirportResource;
import com.nearsoft.flights.rest.resources.CacheResource;
import com.nearsoft.flights.rest.resources.FlightsResource;

public class RestApplication extends ResourceConfig {

	public RestApplication() {
		register(AirportResource.class);
		register(CacheResource.class);
		register(FlightsResource.class);
		register(ExtractionExceptionMapper.class);
	}
}
