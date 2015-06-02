package com.nearsoft.flights.persistence.dao;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;
import com.nearsoft.flights.persistence.dto.AirportDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/jdbc-config.xml")
public class AirportDaoTest {
	
	@Autowired
	private AirportDao airportDao;
	
	@Test
	public void testInsert() throws PersistenceException {
		AirportDto dto = new AirportDto();
		dto.setAirportCode("MX");
		dto.setAirportName("Aeropuerto internacional de la ciudad de Mexico");
		dto.setCity("Mexico");
		dto.setCityCode("MX");
		dto.setCountryCode("MX");
		dto.setCountryName("Mexico");
		dto.setLatitude("1");
		dto.setLongitude("1");
		airportDao.insert(dto);
	}
	
	@Test
	public void findAll() throws PersistenceException {
		this.insertRows();
		Set<AirportDto> airports =airportDao.findAll();
		airports.forEach(p-> System.out.println(p));
	}
	
	
	@Test
	public void findByAirportCode() throws PersistenceException {
		this.insertRows();
		AirportDto airport =airportDao.findByAirportCode("MX800");
		System.out.println(airport);
	}
	
	
	@After
	public void cleanUp() throws PersistenceException {
		airportDao.deleteAll();
	}
	
	@Test
	public void insertRows() throws PersistenceException {
		Set<AirportDto> airports = new HashSet<>();
		for(int i = 0; i < 1000 ; i++) {
			AirportDto dto = new AirportDto();
			dto.setAirportCode("MX"+i);
			dto.setAirportName("Aeropuerto internacional de la ciudad de Mexico");
			dto.setCity("Mexico");
			dto.setCityCode("MX");
			dto.setCountryCode("MX");
			dto.setCountryName("Mexico");
			dto.setLatitude(""+i);
			dto.setLongitude(""+i);
			airports.add(dto);
		}
		airportDao.insert(airports);
	}
}
