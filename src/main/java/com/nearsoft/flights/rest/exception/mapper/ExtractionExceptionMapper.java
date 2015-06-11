package com.nearsoft.flights.rest.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.nearsoft.flights.interfaces.flexapi.extractor.ExtractionException;

public class ExtractionExceptionMapper implements ExceptionMapper<ExtractionException>{

	@Override
	public Response toResponse(ExtractionException exception) {
		return Response.status(Response.Status.BAD_GATEWAY)
			.entity(exception.getMessage())
			.type(MediaType.TEXT_PLAIN)
			.build();
	}
	

}
