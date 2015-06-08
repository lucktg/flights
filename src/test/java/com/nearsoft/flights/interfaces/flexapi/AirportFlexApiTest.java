package com.nearsoft.flights.interfaces.flexapi;

import java.io.IOException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.AirportApiService;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class AirportFlexApiTest {
		
	@Autowired
	private AirportApiService airportApi;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void shouldReturnAirportSet() throws IOException{
		Set<Airport> airports = airportApi.getAllActiveAirports();
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(not(empty())));
	}
}
