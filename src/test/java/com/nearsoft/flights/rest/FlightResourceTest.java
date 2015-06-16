package com.nearsoft.flights.rest;

import javax.ws.rs.core.Application;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nearsoft.flights.rest.resources.FlightsResource;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class FlightResourceTest extends JerseyTest {
	private static final Logger logger = Logger
			.getLogger(FlightResourceTest.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	protected Application configure() {
		BasicConfigurator.configure();
		ResourceConfig rc  = new ResourceConfig();
		rc.register(FlightsResource.class).property("contextConfigLocation", "classpath:spring/application-config.xml");
		return rc;
	}
	
	@Override
    public TestContainerFactory getTestContainerFactory() {
        return new GrizzlyTestContainerFactory();
    }
	
	@Before
	public void setup() {
		jdbcTemplate.update("DELETE FROM FLIGHT");
		jdbcTemplate.update("DELETE FROM AIRPORT");
		jdbcTemplate.update("DELETE FROM AIRLINE");
		
	}
	
	@Test
	public void shouldReturnNotEmptyFlightList() throws InterruptedException {
		String response = target("/flights/roundtrip")
		.queryParam("fromAirport", "MEX")
		.queryParam("departing","2015-06-03")
		.queryParam("returningAirport","GDL")
		.queryParam("returning","2015-06-05").request().get(String.class);
		Assert.assertNotNull(response);
		Assert.assertThat(response, is(not("")));
	}
	
	@After
	public void cleanup() throws InterruptedException {
		
	}
}
