package com.nearsoft.flights.interfaces.flexapi.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonExtractor implements Extractor {
	
	private static Logger logger = Logger.getLogger(JsonExtractor.class);
	
	protected JsonExtractor() {
		
	}
	
	
	private static final ObjectMapper jsonObjectMapper = new ObjectMapper(); 
	
	static {
		logger.debug("Initializing jackson mapper");
		jsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jsonObjectMapper.setVisibilityChecker(jsonObjectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
	}
	
	
	public <K, T> T extract(InputStream in, Function<K,T> function, Class<K> jsonClass) {
		T objectDomain = null;
		try {
			logger.debug("Parsing object from type ["+jsonClass+"] ");
			K jsonObject = jsonObjectMapper.readValue(in, jsonClass);
			objectDomain = function.apply(jsonObject);
		} catch (JsonParseException e) {
			logger.error(e);
			throw new ExtractionException(e);
		} catch (JsonMappingException e) {
			logger.error(e);
			throw new ExtractionException(e);
		} catch (IOException e) {
			logger.error(e);
			throw new ExtractionException(e);
		}
		return objectDomain;
	}
	
	
}
