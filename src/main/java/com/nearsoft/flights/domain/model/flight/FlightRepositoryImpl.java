package com.nearsoft.flights.domain.model.flight;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

import com.googlecode.ehcache.annotations.Cacheable;
import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.domain.model.exception.RepositoryException;
import com.nearsoft.flights.domain.model.flight.Flight.FlightBuilder;
import com.nearsoft.flights.interfaces.FlightApi;
import com.nearsoft.flights.persistence.dao.FlightDao;
import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;
import com.nearsoft.flights.persistence.dto.AirlineDto;
import com.nearsoft.flights.persistence.dto.AirportDto;
import com.nearsoft.flights.persistence.dto.FlightDto;
import com.nearsoft.flights.persistence.dto.TripInformationRequestDto;

public class FlightRepositoryImpl implements FlightRepository {

	private FlightApi flightApi;	
	private FlightDao flightDao;
	
	public FlightRepositoryImpl(FlightApi flightApi, FlightDao flightDao) {
		super();
		this.flightApi = flightApi;
		this.flightDao = flightDao;
	}

	@Cacheable(cacheName="flights")
	@Override
	public Set<Flight> findFlightsByDeparture(TripInformationRequest request) throws RepositoryException {
		try {
			Set<FlightDto> flights = flightDao.findDepartingFlightsByTripInformationRequest(tripInformationRequestToDto(request));
			if(flights == null || flights.isEmpty()) {
				Set<Flight> flightsApi = flightApi.getDepartingFlightsByTripInformation(tripInformationRequestToDto(request));
				flightDao.insertFlights(flightstoFlightsDto(flightsApi));
				return flightsApi;
			} else {
				return flightsDtoToFlights(flights);
			}
		} catch (PersistenceException e) {
			throw new RepositoryException("Error ocurred while performing operations in database for finding flights by departure ["+request+"]", e);
		}
	}
	
	private Set<FlightDto> flightstoFlightsDto(Set<Flight> flights) {
		return flights.stream().map(flight -> flightToFlightDto(flight)).collect(Collectors.toSet());
	}
	
	private Set<Flight> flightsDtoToFlights(Set<FlightDto> flightsDto) {
		return flightsDto.stream().map(flightDto -> flightDtoToFlight(flightDto)).collect(Collectors.toSet());
	}

	private TripInformationRequestDto tripInformationRequestToDto(TripInformationRequest request) {
		TripInformationRequestDto dto = new TripInformationRequestDto();
		dto.setDepartureAirportCode(request.getDepartureAirportCode());
		dto.setDepartureDate(new Timestamp(request.getDepartureDate().getTime()));
		dto.setArrivalAirportCode(request.getArrivalAirportCode());
		return dto;
	}
	
	private FlightDto flightToFlightDto(Flight flight){
		FlightDto dto = new FlightDto();
		dto.setAirline(airlineToAirlineDto(flight.getAirline()));
		dto.setArrivalAirport(airportToAirportDto(flight.getArrival().getAirport()));
		dto.setArrivalDate(new Timestamp(flight.getArrival().getScheduledDate().getTime()));
		dto.setArrivalTerminal(flight.getArrival().getTerminal());
		dto.setDepartureAirport(airportToAirportDto(flight.getDeparture().getAirport()));
		dto.setDepartureDate(new Timestamp(flight.getDeparture().getScheduledDate().getTime()));
		dto.setDepartureTerminal(flight.getDeparture().getTerminal());
		dto.setFlightNumber(flight.getFlightNumber());
		dto.setServiceType(flight.getServiceType());
		return dto;
	}


	private AirlineDto airlineToAirlineDto(Airline airline) {
		AirlineDto dto = new AirlineDto();
		dto.setAirlineCode(airline.getAirlineCode());
		dto.setAirlineName(airline.getName());
		dto.setPhoneNumber(airline.getPhoneNumber());
		return dto;
	}
	
	private AirportDto airportToAirportDto(Airport airport) {
		AirportDto dto = new AirportDto();
		dto.setAirportCode(airport.getAirportCode());
		dto.setAirportName(airport.getName());
		dto.setCity(airport.getCity());
		dto.setCityCode(airport.getCityCode());
		dto.setCountryCode(airport.getCountryCode());
		dto.setCountryName(airport.getCountryName());
		dto.setLatitude(airport.getLatitude());
		dto.setLongitude(airport.getLongitude());
		return dto;
	}
	
	private Flight flightDtoToFlight(FlightDto flightDto) {
		FlightBuilder builder = new FlightBuilder(flightDto.getFlightNumber(),airlineDtoToAirline(flightDto.getAirline()));
		builder.addArrival(new ScheduledTrip(airportDtoToAirport(flightDto.getArrivalAirport()), flightDto.getArrivalDate(), flightDto.getArrivalTerminal()));
		builder.addDeparture(new ScheduledTrip(airportDtoToAirport(flightDto.getDepartureAirport()), flightDto.getDepartureDate(), flightDto.getDepartureTerminal()));
		builder.addServiceType(flightDto.getServiceType());
		return builder.build();
	}
	
	private Airline airlineDtoToAirline(AirlineDto airline) {
		return new Airline(airline.getAirlineCode(), airline.getPhoneNumber(), airline.getAirlineName(), airline.getActive());
	}
	
	private Airport airportDtoToAirport(AirportDto dto) {
		AirportBuilder builder = new AirportBuilder(dto.getAirportCode());
		builder.addCity(dto.getCity());
		builder.addCityCode(dto.getCityCode());
		builder.addCountryCode(dto.getCountryCode());
		builder.addCountryName(dto.getCountryName());
		builder.addLatitude(dto.getLatitude());
		builder.addLongitude(dto.getLongitude());
		builder.addName(dto.getAirportName());
		builder.addStateCode(dto.getStateCode());
		return builder.build();
	}
}
