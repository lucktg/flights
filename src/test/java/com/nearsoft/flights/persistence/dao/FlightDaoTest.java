package com.nearsoft.flights.persistence.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;
import com.nearsoft.flights.persistence.dto.AirlineDto;
import com.nearsoft.flights.persistence.dto.Airport;
import com.nearsoft.flights.persistence.dto.FlightDto;
import com.nearsoft.flights.persistence.dto.TripInformationRequestDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/jdbc-config.xml")
public class FlightDaoTest {
	
	@Autowired
	private FlightDao flightDao;
	
	@Autowired
	private AirlineDao airlineDao;
	
	@Autowired
	private AirportDao airportDao;
	
	@Test
	public void testInsert() throws PersistenceException {
		Calendar calendar  = Calendar.getInstance();
		FlightDto flightDto = new FlightDto();
		flightDto.setFlightNumber("408");
		flightDto.setAirline(getAirline());
		flightDto.setArrivalAirport(getAirport());
		flightDto.setDepartureAirport(getAirport());
		calendar.add(Calendar.DAY_OF_MONTH, 10);
		flightDto.setDepartureDate(new Timestamp(calendar.getTimeInMillis()));
		flightDto.setDepartureTerminal("1");
		calendar.add(Calendar.HOUR_OF_DAY, 3);
		flightDto.setArrivalDate(new Timestamp(calendar.getTimeInMillis()));
		flightDto.setArrivalTerminal("2");
		flightDto.setServiceType("W");
		Set<FlightDto> flights = new HashSet<>();
		flights.add(flightDto);
		flightDao.insertFlights(flights);
	}
	
	@Test
	public void getFlights() throws PersistenceException {
		Calendar calendar  = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 10);
		calendar.add(Calendar.HOUR_OF_DAY, -3);
		TripInformationRequestDto dto = new TripInformationRequestDto();
		dto.setArrivalAirportCode("MX");
		dto.setDepartureAirportCode("MX");
		dto.setDepartureDate(new Timestamp(calendar.getTimeInMillis()));
		Set<FlightDto> flights = flightDao.findDepartingFlightsByTripInformationRequest(dto);
		flights.forEach(p-> System.out.println(p));
	}
	
	@After
	public void cleanup() throws PersistenceException {
		//flightDao.deleteAll();
		//airlineDao.deleteAll();
		//airportDao.deleteAll();
	}
	
	private AirlineDto getAirline(){
		AirlineDto airlineDto = new AirlineDto();
		airlineDto.setAirlineCode("AR");
		airlineDto.setAirlineName("Airline test");
		airlineDto.setPhoneNumber("555-555-55");
		return airlineDto;
	}
	
	private Airport getAirport() {
		Airport airportDto = new Airport();
		airportDto.setAirportCode("MX");
		airportDto.setAirportName("Aeropuerto internacional de la ciudad de Mexico");
		airportDto.setCity("Mexico");
		airportDto.setCityCode("MX");
		airportDto.setCountryCode("MX");
		airportDto.setCountryName("Mexico");
		airportDto.setLatitude("123456.12");
		airportDto.setLongitude("12981.12");
		return airportDto;
	}
}
