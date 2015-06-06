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
import com.nearsoft.flights.persistence.dto.Airline;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/jdbc-config.xml")
public class AirlineDaoTest {
	
	@Autowired
	private AirlineDao airlineDao;
	
	@Test
	public void testFindAll() throws PersistenceException{
		insertAirlines();
		Set<Airline> airlines = airlineDao.findAll();
		airlines.forEach(p-> System.out.println(p));
	}
	
	@Test
	public void testByAirlineCode() throws PersistenceException{
		insertAirlines();
		Airline airline = airlineDao.findByAirlineCode("AR"+323);
		System.out.println(airline);
	}
	
	@Test
	public void insertAirline() throws PersistenceException {
		Airline dto = new Airline();
		dto.setAirlineCode("AR");
		dto.setAirlineName("Name airline");
		dto.setPhoneNumber("555-55-55-12");
		airlineDao.insert(dto);
	}
	
	

	@Test
	public void insertAirlines() throws PersistenceException {
		Set<Airline> airlines = new HashSet<>();
		Airline dto = null;
		for(int i = 0; i < 1000; i++) {
			dto = new Airline();
			dto.setAirlineCode("AR"+i);
			dto.setAirlineName("Name airline " + i);
			dto.setPhoneNumber("555-55-55-12" +i);
			airlines.add(dto);
		}
		airlineDao.insert(airlines);
	}
	
	@After
	public void cleanUp() throws PersistenceException{
		airlineDao.deleteAll();
	}
}
