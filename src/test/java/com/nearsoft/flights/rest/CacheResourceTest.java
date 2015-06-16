package com.nearsoft.flights.rest;

import javax.ws.rs.client.Invocation.Builder;

import javax.ws.rs.core.Application;

import org.apache.log4j.BasicConfigurator;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.hamcrest.Matchers.*;
import com.nearsoft.flights.rest.resources.CacheResource;

public class CacheResourceTest extends JerseyTest {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	protected Application configure() {
		BasicConfigurator.configure();
		ResourceConfig rc  = new ResourceConfig();
		rc.register(CacheResource.class).property("contextConfigLocation", "classpath:spring/application-config.xml");
		return rc;
	}
	
	@Override
    public TestContainerFactory getTestContainerFactory() {
        return new GrizzlyTestContainerFactory();
    }
	
	@Test
	public void shouldDeleteCache() {
		Builder builder = target("/cache/clean").request();
		Assert.assertThat(builder.get().getStatus(), is(200));
	}
}
