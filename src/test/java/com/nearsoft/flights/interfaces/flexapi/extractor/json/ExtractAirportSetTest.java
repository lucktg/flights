package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

import javax.ws.rs.core.MediaType;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.domain.services.AirportJsonWrapper;
import com.nearsoft.flights.interfaces.flexapi.domain.services.ExtractorJsonUtils;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;
import com.nearsoft.flights.interfaces.flexapi.extractor.MediaTypeExtractorFactory;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class ExtractAirportSetTest {
	private static Logger logger = Logger.getLogger(ExtractAirportSetTest.class);
	
	private static final String PORTLAND_AIRPORT_JSON ="airport.json";
	private static final String PORTLAND_AIRPORT_CODE = "PDX";
	
	private InputStream json;
	
	@Autowired
	private Extractor extractor = MediaTypeExtractorFactory.getInstance().createExtractor(MediaType.APPLICATION_JSON);
	
	@Mock
	private Function<AirportJsonWrapper,Airport> function;
	
	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	public ExtractAirportSetTest() {
		BasicConfigurator.configure();
	}

	@Before
	public void setup() throws FileNotFoundException {
		MockitoAnnotations.initMocks(this);
		json = ExtractAirportSetTest.class.getResourceAsStream(PORTLAND_AIRPORT_JSON);
	}
	
	@Test
	public void shouldReturnAirport() {
		logger.debug("Starting test shouldReturnAirport");
		when(function.apply(any())).thenReturn(Airport.emptyAirport());
		Airport airport = extractor.extract(json, function, AirportJsonWrapper.class);
		Assert.assertNotNull(airport);
		verify(function,times(1)).apply(any(AirportJsonWrapper.class));
	}
	
	@Test
	public void shouldReturnAirportWithData() {
		logger.debug("Starting test shouldReturnAirportWithData");
		Airport airport = extractor.extract(json, ExtractorJsonUtils::airportPojoToAirport, AirportJsonWrapper.class);
		Assert.assertNotNull(airport);
		Assert.assertThat(airport.getAirportCode(), equalTo(PORTLAND_AIRPORT_CODE));
	}
	
	
	
	
	@After
	public void cleanup() throws IOException {
		if (json != null ) json.close();
	}
}
