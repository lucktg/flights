package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.domain.model.flight.Airline;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.Flight.FlightBuilder;
import com.nearsoft.flights.domain.model.flight.ScheduledTrip;
import com.nearsoft.flights.interfaces.flexapi.extractor.json.AirportSetExtractorJson.AirportsJson;
import com.nearsoft.flights.interfaces.flexapi.extractor.json.FlightSetExtractorJson.FlightsJson;

public class ExtractorUtils {
	

	protected static Airport airportPojoToAirport(AirportPojo airportPojo){
		AirportBuilder airportBuilder = new AirportBuilder(airportPojo.getFs());
		airportBuilder.addCity(airportPojo.getCity());
		airportBuilder.addCityCode(airportPojo.getCityCode());
		airportBuilder.addCountryCode(airportPojo.getCountryCode());
		airportBuilder.addCountryName(airportPojo.getCountryName());
		airportBuilder.addLatitude(airportPojo.getLatitude());
		airportBuilder.addLongitude(airportPojo.getLongitude());
		airportBuilder.addName(airportPojo.getName());
		airportBuilder.addStateCode(airportPojo.getStateCode());
		return airportBuilder.build();
	}
	
	protected static Airline airlinePojoToAirline(AirlinePojo airlinePojo) {
		return new Airline(airlinePojo.getFs(), airlinePojo.getPhoneNumber(), airlinePojo.getName(), airlinePojo.getActive());
	}
	
	protected static Flight flightPojoToFlight(FlightScheduledPojo flightPojo) {
		Map<String, AirlinePojo> airlinesMap = flightPojo.getAppendix().getAirlines().stream().collect(Collectors.toMap(AirlinePojo::getFs, p -> p));
		Map<String, AirportPojo> airportsMap = flightPojo.getAppendix().getAirports().stream().collect(Collectors.toMap(AirportPojo::getFs, p -> p));
		FlightBuilder builder = new FlightBuilder(flightPojo.getFlightNumber(), 
				airlinePojoToAirline(airlinesMap.get(flightPojo.getCarrierFSCode())));
		builder.addArrival(getScheduledTrip(airportPojoToAirport(airportsMap.get(flightPojo.getArrivalAirportFSCode())),
				flightPojo.getArrivalTime(), flightPojo.getArrivalTerminal()));
		builder.addDeparture(getScheduledTrip(airportPojoToAirport(airportsMap.get(flightPojo.getDepartureAirportFSCode())),
				flightPojo.getDepartureTime(), flightPojo.getDepartureTerminal()));
		builder.addServiceClasses(flightPojo.getServiceClasses());
		builder.addServiceType(flightPojo.getServiceType());
		return builder.build();
	}
	
	protected static Set<Airport> airportJsonSetToAirportSet(AirportsJson airports){
		return airports.getAirports().stream().map(airport -> airportPojoToAirport(airport)).collect(Collectors.toSet());
	}
	
	protected static Set<Flight> flightJsonSetToFlightSet(FlightsJson flightsJson){
		return flightsJson.getFlights().stream().map(airport -> flightPojoToFlight(airport)).collect(Collectors.toSet());
	}
	
	private static ScheduledTrip getScheduledTrip(Airport airport, Date scheduledDate, String terminal) {
		return new ScheduledTrip(airport, scheduledDate, terminal);
	}
	
	
	
}