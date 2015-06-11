package com.nearsoft.flights.interfaces.flexapi.extractor;

import java.io.InputStream;
import java.util.function.Function;

public interface Extractor {
	public <K,T> T extract(InputStream in, Function<K,T> function, Class<K> clazz);
}
