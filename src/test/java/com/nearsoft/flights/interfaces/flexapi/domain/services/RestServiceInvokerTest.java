package com.nearsoft.flights.interfaces.flexapi.domain.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.ResourceAccessException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class RestServiceInvokerTest {
	
	private static final Logger logger = Logger.getLogger(RestServiceInvokerTest.class);
	
	@Mock
	private Function<Object, Object> function;
	
	@Autowired
	private RestServiceInvoker restServiceInvoker;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	public RestServiceInvokerTest() {
		BasicConfigurator.configure();
	}
	
	@Test
	public void shouldReturnNotNull() {
		when(function.apply(any())).thenReturn(new Object());
		Object value = restServiceInvoker.invoke(
				UriBuilder.fromPath("http://jsonplaceholder.typicode.com/posts/1").build(), 
				function, 
				Object.class);
		logger.debug(value);
		verify(function, times(1)).apply(any(Object.class));
		Assert.assertNotNull(value);
	}
	
	
	@Test
	public void shouldThrownExtractorException() {
		expectedException.expect(ExtractorException.class);
		expectedException.expectMessage("No extractor for media type ");
		restServiceInvoker.invoke(
				UriBuilder.fromPath("http://www.google.com").build(), 
				function, 
				Object.class);		
	}
	
	@Test
	public void shouldThrownResourceAccessException() {
		expectedException.expect(ResourceAccessException.class);
		expectedException.expectMessage("I/O error on GET request");
		restServiceInvoker.invoke(
				UriBuilder.fromPath("http://www.g.com").build(), 
				function, 
				Object.class);		
	}
	
	@Test
	public void shouldReturnNull() {
		when(function.apply(any())).thenReturn(null);
		Object value = restServiceInvoker.invoke(
				UriBuilder.fromPath("http://jsonplaceholder.typicode.com/posts/1").build(), 
				function, 
				Object.class);
		logger.debug(value);
		verify(function, times(1)).apply(any(Object.class));
		
	}
}
