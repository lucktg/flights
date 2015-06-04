package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
		if (airportPojo == null) return Airport.emptyAirport();
		
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
		
		return airlinePojo != null ? 
				new Airline(airlinePojo.getFs(), airlinePojo.getPhoneNumber(), airlinePojo.getName(), airlinePojo.getActive()) : 
						Airline.emptyAirline();
	}
	
	protected static Flight flightPojoToFlight(FlightScheduledPojo flightPojo, Map<String, AirlinePojo> airlinesMap, Map<String, AirportPojo> airportsMap) {
		if (flightPojo == null) return Flight.emptyFlight();
		airlinesMap = airlinesMap != null ? airlinesMap : Collections.emptyMap();
		airportsMap = airportsMap != null ? airportsMap : Collections.emptyMap();
		
		FlightBuilder builder = new FlightBuilder(flightPojo.getFlightNumber(), 
				airlinePojoToAirline(airlinesMap.get(flightPojo.getCarrierFsCode())));
		builder.addArrival(getScheduledTrip(airportPojoToAirport(airportsMap.get(flightPojo.getArrivalAirportFsCode())),
				flightPojo.getArrivalTime(), flightPojo.getArrivalTerminal()));
		builder.addDeparture(getScheduledTrip(airportPojoToAirport(airportsMap.get(flightPojo.getDepartureAirportFsCode())),
				flightPojo.getDepartureTime(), flightPojo.getDepartureTerminal()));
		builder.addServiceClasses(flightPojo.getServiceClasses());
		builder.addServiceType(flightPojo.getServiceType());
		return builder.build();
	}
	
	protected static Set<Airport> airportJsonSetToAirportSet(AirportsJson airports){
		return airports != null && airports.getAirports() != null ? 
				airports.getAirports().stream().map(airport -> airportPojoToAirport(airport)).collect(Collectors.toSet()) :
					Collections.emptySet();
	}
	
	protected static Set<Flight> flightJsonSetToFlightSet(FlightsJson flightsJson){
		if (flightsJson == null || flightsJson.getFlights() == null) return Collections.emptySet();
		AppendixPojo appendix = flightsJson.getAppendix() != null ? flightsJson.getAppendix() : new AppendixPojo();
		Map<String, AirlinePojo> airlinesMap = appendix.getAirlines() != null ? 
				appendix.getAirlines().stream().collect(Collectors.toMap(AirlinePojo::getFs, p -> p)) :
					Collections.emptyMap();
		Map<String, AirportPojo> airportsMap = appendix.getAirports() != null ? 
				appendix.getAirports().stream().collect(Collectors.toMap(AirportPojo::getFs, p -> p)) :
					Collections.emptyMap();
		Iterator<FlightScheduledPojo> it = flightsJson.getFlights().iterator();
		FlightScheduledPojo pojo = null;
		Set<Flight> flights = new HashSet<>();
		while (it != null && it.hasNext()) {
			pojo = it.next();
			flights.add(flightPojoToFlight(pojo,airlinesMap, airportsMap));
		}
		return flights;
	}
	
	private static ScheduledTrip getScheduledTrip(Airport airport, Date scheduledDate, String terminal) {
		return new ScheduledTrip(airport, scheduledDate, terminal);
	}
	
	
	
}
