package com.nearsoft.flights.rest;

import javax.ws.rs.client.Invocation.Builder;

import javax.ws.rs.core.Application;

import org.apache.log4j.BasicConfigurator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import com.nearsoft.flights.rest.resources.FlightsResource;

public class FlightResourceTest extends JerseyTest {
	
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
	
	@Test
	public void shouldReturnEmptyAirportsList() {
		Builder builder = target("/flights/roundtrip")
		.queryParam("fromAirport", "MEX")
		.queryParam("departing","2015-06-03")
		.queryParam("returningAirport","GDL")
		.queryParam("returning","2015-06-05").request();
		System.out.println(builder.get(String.class));
	}
}
