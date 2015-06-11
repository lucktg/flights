package com.nearsoft.flights.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class Utils {

	public static boolean isNull(Object obj){
		return obj == null;
	}
	
	public static boolean isEmptyString(String string) {
		return isNull(string) || "".equals(string);
	}
	
	public static <E> boolean isCollectionEmpty(Collection<E> collection) {
		return isNull(collection) || collection.isEmpty();
	}
	
	public static <K,V> Map<K,V> getValidatedMap(Map<K,V> map) {
		return isNull(map) ? Collections.emptyMap() : map;
	}
}
