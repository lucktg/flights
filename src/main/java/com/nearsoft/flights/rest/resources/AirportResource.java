package com.nearsoft.flights.rest.resources;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.services.AirportsService;

@Component
@Path("/airports")
public class AirportResource {
	
	private static final Logger logger = Logger.getLogger(AirportResource.class);
	
	@Autowired
	private AirportsService airportService;
	
	@GET
	@Path("active")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Airport> getAllActiveAirports() {
		logger.debug("Searching all airports");
		
		return airportService.getActiveAirports();
	}
	
	@GET
	@Path("{airportCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Airport getByAirportCode(@PathParam("airportCode") String airportCode) {
		logger.debug("Searching airport by airportCode["+airportCode+"]");
		return airportService.getAirportByCode(airportCode);
	}
	
}
