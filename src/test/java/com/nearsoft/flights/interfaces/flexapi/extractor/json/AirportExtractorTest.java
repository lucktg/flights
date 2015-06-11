package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.interfaces.flexapi.domain.services.AirportJsonWrapper;
import com.nearsoft.flights.interfaces.flexapi.domain.services.ExtractorJsonUtils;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;


@RunWith(BlockJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class AirportExtractorTest {
	private static Logger logger = Logger.getLogger(AirportExtractorTest.class);
	
	private InputStream json;
	private Extractor extractor = new JsonExtractor();
	
	public AirportExtractorTest() {
		BasicConfigurator.configure();
	}

	@Before
	public void setup() throws FileNotFoundException {
		json = AirportExtractorTest.class.getResourceAsStream("airport.json");
	}
	
	@Test
	public void shouldReturnAirport() throws IOException {
		Airport airport = extractor.extract(json, ExtractorJsonUtils::airportPojoToAirport, AirportJsonWrapper.class);
		logger.debug(airport);
		Assert.assertNotNull(airport);
	}
}
