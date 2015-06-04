package com.nearsoft.flights.rest.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nearsoft.flights.domain.services.CacheService;

@Component
@Path("cache")
public class CacheResource {

	@Autowired
	private CacheService cacheService;
	
	public enum CacheType{ database,memory}
	
	@Path("clean")
	public void cleanCache(@QueryParam("type") String cacheType) {
		cacheService.clean(CacheType.valueOf(cacheType));
	}

}
