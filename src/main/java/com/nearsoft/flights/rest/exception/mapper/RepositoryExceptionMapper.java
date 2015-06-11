package com.nearsoft.flights.rest.exception.mapper;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.nearsoft.flights.domain.model.exception.RepositoryException;

public class RepositoryExceptionMapper implements ExceptionMapper<RepositoryException> {

	@Override
	public Response toResponse(RepositoryException exception) {
		return Response.status(Response.Status.SERVICE_UNAVAILABLE)
			.entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}

}
