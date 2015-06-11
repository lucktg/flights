package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.domain.services.AirportJsonSetWrapper;
import com.nearsoft.flights.interfaces.flexapi.domain.services.ExtractorJsonUtils;
import com.nearsoft.flights.interfaces.flexapi.extractor.ExtractionException;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class ExtractAirportTest {
	private static Logger logger = Logger.getLogger(ExtractAirportTest.class);
	
	private static final String AIRPORTS_JSON ="airports.json";
	private static final Airport manchesterAirport = new AirportBuilder("MAN").addLatitude("53.362908").addLongitude("-2.273354").build();
	
	private InputStream json;
	
	private Extractor extractor = new JsonExtractor();
	
	@Mock
	private Function<AirportJsonSetWrapper,Set<Airport>> function;
	
	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	public ExtractAirportTest() {
		BasicConfigurator.configure();
	}

	@Before
	public void setup() throws FileNotFoundException {
		MockitoAnnotations.initMocks(this);
		json = ExtractAirportTest.class.getResourceAsStream(AIRPORTS_JSON);
	}
	
	@Test
	public void shouldReturnAirportSet() {
		logger.debug("Starting test shouldReturnAirportSet");
		when(function.apply(any())).thenReturn(Collections.emptySet());
		Set<Airport> airports = extractor.extract(json, function, AirportJsonSetWrapper.class);
		Assert.assertNotNull(airports);
		Assert.assertThat(airports, is(empty()));
		verify(function,times(1)).apply(any(AirportJsonSetWrapper.class));
	}
	
	@Test
	public void shouldReturnAirportSetWithData() {
		logger.debug("Starting test shouldReturnAirportSetWithData");
		Set<Airport> airports = extractor.extract(json, ExtractorJsonUtils::airportJsonSetToAirportSet, AirportJsonSetWrapper.class);
		Assert.assertNotNull(airports);
		Assert.assertThat(airports.size(), is(264));
		Assert.assertThat(airports, is(not(empty())));
		Assert.assertThat(airports.contains(manchesterAirport), is(true));
	}
	
	@Test
	public void shouldReturnNullSet() {
		logger.debug("Starting test shouldReturnNullSet");
		when(function.apply(any())).thenReturn(null);
		Set<Airport> airports = extractor.extract(json, function, AirportJsonSetWrapper.class);
		Assert.assertNull(airports);
		verify(function,times(1)).apply(any(AirportJsonSetWrapper.class));
	}
	
	@After
	public void cleanup() throws IOException {
		if (json != null ) json.close();
	}
}
