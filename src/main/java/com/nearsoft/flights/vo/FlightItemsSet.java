package com.nearsoft.flights.vo;

import java.util.Set;

public abstract class FlightItemsSet<E extends FlightItem> {
	
	public abstract Set<E> getItems();

}
