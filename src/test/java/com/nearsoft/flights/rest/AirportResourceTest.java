package com.nearsoft.flights.rest;



import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.nearsoft.flights.rest.resources.AirportResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class AirportResourceTest extends JerseyTest {
	Logger logger = Logger.getLogger(AirportResourceTest.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	protected Application configure() {
		BasicConfigurator.configure();
		ResourceConfig rc  = new ResourceConfig();
		rc.register(AirportResource.class).property("contextConfigLocation", "classpath:spring/application-config.xml");
		
		return rc;
	}
	
	
	public void setup() {
		jdbcTemplate.update("DELETE FROM AIRPORT");
		jdbcTemplate.update("DELETE FROM AIRLINE");
		jdbcTemplate.update("DELETE FROM FLIGHT");
	}
	
	@Override
    public TestContainerFactory getTestContainerFactory() {
        return new GrizzlyTestContainerFactory();
    }
	
	@Test
	public void shouldGetNotEmptyAirports() {
		Set response = target("/airports/active").request().get(Set.class);
		int inserted = jdbcTemplate.queryForObject("select count(*) from airport", Integer.class);
		logger.info(response.size());
		Assert.notNull(response);
		Assert.isTrue(inserted == response.size(), "Airports from api service are not the same");
		response = target("/airports/active").request().get(Set.class);
		logger.info(response.size());
		Assert.notNull(response);
		
	}
	
}
