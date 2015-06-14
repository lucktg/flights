package com.nearsoft.flights.interfaces.flexapi.domain.services;

import static com.nearsoft.flights.util.Utils.getValidatedMap;
import static com.nearsoft.flights.util.Utils.isNull;

import java.util.Collections;
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
import com.nearsoft.flights.interfaces.flexapi.domain.model.AirlinePojo;
import com.nearsoft.flights.interfaces.flexapi.domain.model.AirportPojo;
import com.nearsoft.flights.interfaces.flexapi.domain.model.AppendixPojo;
import com.nearsoft.flights.interfaces.flexapi.domain.model.FlightScheduledPojo;

public class ExtractorJsonUtils {
	

	public static Airport airportPojoToAirport(AirportJsonWrapper wrapper){
		AirportPojo airportPojo = wrapper.getAirport();
		return airportPojoToAirport(airportPojo);
	}
	
	
	public static Airport airportPojoToAirport(AirportPojo airportPojo){
		if (isNull(airportPojo)) return Airport.emptyAirport();
		
		AirportBuilder airportBuilder = new AirportBuilder(airportPojo.getFs());
		airportBuilder.addCity(airportPojo.getCity());
		airportBuilder.addCityCode(airportPojo.getCityCode());
		airportBuilder.addCountryCode(airportPojo.getCountryCode());
		airportBuilder.addCountryName(airportPojo.getCountryName());
		airportBuilder.addLatitude(airportPojo.getLatitude());
		airportBuilder.addLongitude(airportPojo.getLongitude());
		airportBuilder.addAirportName(airportPojo.getName());
		airportBuilder.addStateCode(airportPojo.getStateCode());
		return airportBuilder.build();
	}
	
	protected static Airline airlinePojoToAirline(AirlinePojo airlinePojo) {
		if (!isNull(airlinePojo)) {
			return new Airline(airlinePojo.getFs(), airlinePojo.getPhoneNumber(), airlinePojo.getName(), airlinePojo.getActive());
		}
		return Airline.emptyAirline();
	}
	
	public static Flight flightPojoToFlight(FlightScheduledPojo flightPojo, Map<String, AirlinePojo> airlinesMap, Map<String, AirportPojo> airportsMap) {
		if (isNull(flightPojo)) return Flight.emptyFlight();
		airlinesMap = getValidatedMap(airlinesMap);
		airportsMap = getValidatedMap(airportsMap);
		Airline airline = airlinePojoToAirline(airlinesMap.get(flightPojo.getCarrierFsCode()));
		Airport arrivalAirport = airportPojoToAirport(airportsMap.get(flightPojo.getArrivalAirportFsCode()));
		Airport departureAirport = airportPojoToAirport(airportsMap.get(flightPojo.getDepartureAirportFsCode()));
		ScheduledTrip arrival = getScheduledTrip(arrivalAirport,flightPojo.getArrivalTime(), flightPojo.getArrivalTerminal());
		ScheduledTrip departure = getScheduledTrip(departureAirport,flightPojo.getDepartureTime(), flightPojo.getDepartureTerminal());
		FlightBuilder builder = new FlightBuilder(flightPojo.getFlightNumber(), airline);
		builder.addArrival(arrival);
		builder.addDeparture(departure);
		builder.addServiceClasses(flightPojo.getServiceClasses());
		builder.addServiceType(flightPojo.getServiceType());
		return builder.build();
	}
	
	public static Set<Airport> airportJsonSetToAirportSet(AirportJsonSetWrapper airports){
		if(isNull(airports) || isNull(airports.getAirports())) return Collections.emptySet();
		return airports.getAirports().stream().map(airport -> airportPojoToAirport(airport)).collect(Collectors.toSet());
	}
	
	public static Set<Flight> flightJsonSetToFlightSet(FlightJsonSetWrapper flightsJson){
		if (isNull(flightsJson) || isNull(flightsJson.getFlights())) return Collections.emptySet();
		AppendixPojo appendix = flightsJson.getAppendix();
		Set<AirlinePojo> airlines = appendix.getAirlines();
		Set<AirportPojo> airports = appendix.getAirports();
		if(isNull(appendix)) appendix = new AppendixPojo();
		Map<String, AirlinePojo> airlinesMap = !isNull(airlines) ? 
				airlines.stream().collect(Collectors.toMap(AirlinePojo::getFs, p -> p)) :
					Collections.emptyMap();
		Map<String, AirportPojo> airportsMap = !isNull(airports) ? 
				airports.stream().collect(Collectors.toMap(AirportPojo::getFs, p -> p)) :
					Collections.emptyMap();
		Set<Flight> flights = flightsJson.getFlights().stream().map(p -> flightPojoToFlight(p,airlinesMap,airportsMap)).collect(Collectors.toSet());;

		return flights;
	}
	
	private static ScheduledTrip getScheduledTrip(Airport airport, Date scheduledDate, String terminal) {
		return new ScheduledTrip(airport, scheduledDate, terminal);
	}

}
