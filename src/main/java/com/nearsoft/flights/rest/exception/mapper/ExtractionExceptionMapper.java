package com.nearsoft.flights.rest.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ExtractionExceptionMapper implements ExceptionMapper<RuntimeException>{

	
	@Override
	public Response toResponse(RuntimeException exception) {
		exception.printStackTrace();
		return Response.status(Response.Status.BAD_GATEWAY)
			.entity(exception.getMessage())
			.type(MediaType.TEXT_PLAIN)
			.build();
	}
	

}
