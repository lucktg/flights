package com.nearsoft.flights.rest.resources;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nearsoft.flights.service.AirportsService;
import com.nearsoft.flights.vo.AirportDTO;

@Component
@Path("/airports")
public class AirportResource {

	@Autowired
	private AirportsService airportService;
	
	@GET
	@Path("active")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<AirportDTO> getAllActiveAirports(){
		return airportService.getActiveAirports();
	}
	
}
