package com.nearsoft.flights.rest.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nearsoft.flights.domain.model.flight.RoundTrip;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;
import com.nearsoft.flights.domain.services.FlightsService;

@Component
@Path("/flights")
public class FlightsResource {
	
	@Autowired
	private FlightsService flightsService;
	
	@GET
	@Path("roundtrip")
	@Produces(MediaType.APPLICATION_JSON	)
	public RoundTrip getRoundTripFights(@NotEmpty @QueryParam("fromAirport") String fromAirport, 
			@NotEmpty @QueryParam("departing") String departing,
			@NotEmpty @QueryParam ("returningAirport") String returningAirport,
			@NotEmpty @QueryParam("returning") String returning) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date departureDate = format.parse(departing);
			Date returningDate = format.parse(returning);
			if(departureDate.after(returningDate)) 
				throw new WebApplicationException("Departure date must be lower than returning date", Response.Status.BAD_REQUEST);
			return flightsService.getRoundTripFlights(getTripInformation(fromAirport, returningAirport, departureDate), 
					getTripInformation(returningAirport, fromAirport, returningDate));
		} catch (Throwable e) {
			e.printStackTrace();
			throw new WebApplicationException("Service unavailable", e, Response.Status.INTERNAL_SERVER_ERROR);
		}
		/*} catch (ServiceException e) {
			e.printStackTrace();
			throw new WebApplicationException("Service unavailable", e, Response.Status.INTERNAL_SERVER_ERROR);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new WebApplicationException("Wrong date format, must be yyyy-mm-dd", e, Response.Status.BAD_REQUEST);
		}*/
	}
	
	private TripInformationRequest getTripInformation(String airportCodeOrigin, String airportCodeDestiny, Date date) throws ParseException {
		return new TripInformationRequest(airportCodeOrigin, date,airportCodeDestiny);
	}
}
