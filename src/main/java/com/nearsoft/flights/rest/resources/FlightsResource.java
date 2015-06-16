package com.nearsoft.flights.rest.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nearsoft.flights.domain.model.RoundTrip;
import com.nearsoft.flights.domain.model.TripInformationRequest;
import com.nearsoft.flights.domain.services.FlightsService;

@Component
@Path("/flights")
public class FlightsResource {
	
	private static final String DATE_PATTERN = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|1\\d{1}|2\\d{1}|3[0-1])";

	private static final Logger logger = Logger.getLogger(FlightsResource.class);
	
	@Autowired
	private FlightsService flightsService;
	
	@GET
	@Path("roundtrip")
	@Produces(MediaType.APPLICATION_JSON)
	public RoundTrip getRoundTripFights(@NotEmpty @QueryParam("fromAirport") String fromAirport, 
			@NotEmpty @QueryParam("departing") String departing,
			@NotEmpty @QueryParam ("returningAirport") String returningAirport,
			@NotEmpty @QueryParam("returning") String returning) {
		logger.debug("Searching rondTrips with parameters:  fromAirport["+fromAirport+"], departing["+departing+"], returningAirport["+returningAirport+"], returning["+returning+"]");
		validateInputData(fromAirport,departing,returningAirport,returning);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date departureDate = format.parse(departing);
			Date returningDate = format.parse(returning);
			if(departureDate.after(returningDate)) 
				throw new WebApplicationException("Departure date must be lower than returning date", Response.Status.BAD_REQUEST);
			RoundTrip roundTrip = flightsService.getRoundTripFlights(getTripInformation(fromAirport, returningAirport, departureDate), 
					getTripInformation(returningAirport, fromAirport, returningDate));
			logger.info("TERMINO DE EJECUTAR LA BUSQUEDA DE VUELOS");
			logger.info(roundTrip.getDestinyFlights().size());
			logger.info(roundTrip.getOriginFlights().size());
			return roundTrip;
		} catch (ParseException e) {
			throw new WebApplicationException("Wrong date format, must be yyyy-MM-dd", e, Response.Status.BAD_REQUEST);
		}
	}
	
	private TripInformationRequest getTripInformation(String airportCodeOrigin, String airportCodeDestiny, Date date) throws ParseException {
		return new TripInformationRequest(airportCodeOrigin, date,airportCodeDestiny);
	}
	
	private void validateInputData(String fromAirport, String departing, String returningAirport,String returning){
		if(isNullOrEmpty(fromAirport) || isNullOrEmpty(departing) || isNullOrEmpty(returningAirport) || isNullOrEmpty(returning))
			throw new WebApplicationException("Required information is missing", Response.Status.BAD_REQUEST);
		if(!Pattern.matches(DATE_PATTERN,departing) || !Pattern.matches(DATE_PATTERN,returning)) 
			throw new WebApplicationException("Wrong date format, must be yyyy-MM-dd", Response.Status.BAD_REQUEST);
	}	
	
	private boolean isNullOrEmpty(String string) {
		return string == null || "".equals(string.trim());
	}

}
