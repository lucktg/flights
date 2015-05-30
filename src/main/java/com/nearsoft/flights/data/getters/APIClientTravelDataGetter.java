package com.nearsoft.flights.data.getters;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.nearsoft.flights.interfaces.TravelAPIClient;
import com.nearsoft.flights.vo.AirportDTO;
import com.nearsoft.flights.vo.Airports;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.FlightItem;
import com.nearsoft.flights.vo.FlightItemsSet;
import com.nearsoft.flights.vo.Flights;
import com.nearsoft.flights.vo.TripInformationRequest;

public class APIClientTravelDataGetter extends Observable implements TravelDataGetter {
	
	private TravelAPIClient travelAPIClient;

	public APIClientTravelDataGetter(TravelAPIClient travelAPIClient, List<Observer> observers) {
		this.travelAPIClient = travelAPIClient;
		if(observers != null) observers.forEach(p -> this.addObserver(p));
	}
	
	@Override
	public Set<AirportDTO> getAllActiveAirports() {
		Airports airports = travelAPIClient.getAllActiveAirports();
		return notify(airports);
	}

	@Override
	public Set<Flight> getDepartingFlightsByRouteNDate(TripInformationRequest tripInformation) {
		Flights flights = travelAPIClient.getDepartingFlightsByRouteAndDate(tripInformation);
		return notify(flights);
	}
	
	private <K extends FlightItem> Set<K> notify(FlightItemsSet<K> flightItemSet) {
		setChanged();
		notifyObservers(flightItemSet);
		return flightItemSet != null && flightItemSet.getItems() != null ? flightItemSet.getItems() : Collections.emptySet();
	}


}
