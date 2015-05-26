package com.nearsoft.flights.rest;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import com.fasterxml.jackson.databind.util.JSONPObject;

public class AirportResourceTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		ResourceConfig rc  = new ResourceConfig();
		rc.register(AirportResource.class).property("contextConfigLocation", "classpath:**/my-web-test-context.xml");
		return rc;
	}
	@Override
    public TestContainerFactory getTestContainerFactory() {
        return new GrizzlyTestContainerFactory();
    }
	
	@Test
	public void shouldReturnEmptyAirportsList() {
		String json = target("airport/active").request().get(String.class);
		System.out.println(json);
	}
	
}
