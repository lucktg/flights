package com.nearsoft.flights.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nearsoft.flights.domain.services.CacheService;

@Component
@Path("/cache")
public class CacheResource {

	@Autowired
	private CacheService cacheService;
	
	
	@Path("clean")
	@GET
	public Response cleanCache() {
		cacheService.clean();
		return Response.ok().build();
	}

}
