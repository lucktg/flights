package com.nearsoft.flights.rest.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nearsoft.flights.service.FlightsService;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.RoundTrip;
import com.nearsoft.flights.vo.TripInformation;

@Component
@Path("flights")
public class FlightsResource {
	
	@Autowired
	private FlightsService flightsService;
	
	@GET
	@Path("/roundtrip/from/{departureAirportCode}/{departureDate}/to/{arrivalAirportCode}/{returnDate}")
	@Produces(MediaType.APPLICATION_JSON	)
	public RoundTrip getRoundTripFights(@PathParam("departureAirportCode") String departureAirportCode, 
			@PathParam("departureDate") String departureDate,
			@PathParam ("arrivalAirportCode") String arrivalAirportCode,
			@PathParam("returnDate") String returnDate) {
		try {
			return flightsService.getRoundTripFlights(getTripInformation(departureAirportCode, arrivalAirportCode, departureDate), 
					getTripInformation(arrivalAirportCode, departureAirportCode, returnDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private TripInformation getTripInformation(String airportCodeOrigin, String airportCodeDestiny, String departureDate) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		return new TripInformation(airportCodeDestiny, airportCodeDestiny, format.parse(departureDate));
	}

}
