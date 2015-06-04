package com.nearsoft.flights.rest.resources;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nearsoft.flights.domain.services.CacheService;

@Component
@Path("cache")
public class CacheResource {

	@Autowired
	private CacheService cacheService;
	
	public enum CacheType{ airports,flights}
	
	@Path("clean")
	public void cleanCache(@QueryParam("type") String cacheType) {
		cacheService.clean(cacheType != null ? CacheType.valueOf(cacheType) : null);
	}

}
