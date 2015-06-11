package com.nearsoft.flights.interfaces.flexapi.extractor.json;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.function.Function;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import com.nearsoft.flights.interfaces.flexapi.extractor.ExtractionException;
import com.nearsoft.flights.interfaces.flexapi.extractor.Extractor;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class ExtractorTest {
private static Logger logger = Logger.getLogger(ExtractorTest.class);
	
	private static final String UNRECOGNIZED_TOKEN_MESSAGE = "Unrecognized token";
	private static final String NO_CONTENT_MESSAGE = "No content to map due to end-of-input";
	private static final String PLAIN_TEXT_JSON = "plainText.json";
	private static final String EMPTY_JSON = "empty.json";
	
	private InputStream json;
	
	private Extractor extractor = new JsonExtractor();
	
	@Mock
	private Function<Object,Object> function;
	
	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	public ExtractorTest() {
		BasicConfigurator.configure();
	}

	@Before
	public void setup() throws FileNotFoundException {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldThrownExceptionWithUnrecognizedTokenMessage() {
		logger.debug("Starting test shouldThrownExceptionWithUnrecognizedTokenMessage");
		expected.expect(ExtractionException.class);
		json = ExtractorTest.class.getResourceAsStream(PLAIN_TEXT_JSON);
		expected.expectMessage(UNRECOGNIZED_TOKEN_MESSAGE);
		when(function.apply(any())).thenReturn(new Object());
		extractor.extract(json, function, Object.class);
	}
	
	@Test
	public void shouldThrownExceptionWithNoContentMessage() {
		logger.debug("Starting test shouldThrownExceptionWithNoContentMessage");
		expected.expect(ExtractionException.class);
		expected.expectMessage(NO_CONTENT_MESSAGE);
		json = ExtractorTest.class.getResourceAsStream(EMPTY_JSON);
		when(function.apply(any())).thenReturn(new Object());
		extractor.extract(json, function, Object.class);
	}
	
}
