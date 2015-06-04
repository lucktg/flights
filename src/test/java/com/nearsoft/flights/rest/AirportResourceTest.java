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
import org.springframework.util.Assert;

import com.nearsoft.flights.rest.resources.AirportResource;

public class AirportResourceTest extends JerseyTest {
	Logger logger = Logger.getLogger(AirportResourceTest.class);
	
	@Override
	protected Application configure() {
		BasicConfigurator.configure();
		ResourceConfig rc  = new ResourceConfig();
		rc.register(AirportResource.class).property("contextConfigLocation", "classpath:spring/application-config.xml");
		
		return rc;
	}
	@Override
    public TestContainerFactory getTestContainerFactory() {
        return new GrizzlyTestContainerFactory();
    }
	
	@Test
	public void shouldNotEmptyAirportsList() {
		Set response = target("/airports/active").request().get(Set.class);
		Assert.notNull(response);
	}
	
}
