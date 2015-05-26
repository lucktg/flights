package com.nearsoft.flights.service;

import java.util.Set;

import com.nearsoft.flights.data.getters.TravelDataGetter;
import com.nearsoft.flights.vo.Airport;
public class AirportsServiceImpl implements AirportsService {

	private TravelDataGetter databaseTravelDataGetter;
	private TravelDataGetter apiClientTravelDataGetter;
	
	public AirportsServiceImpl(TravelDataGetter apiGetter,
			TravelDataGetter dbGetter) {
		this.apiClientTravelDataGetter = apiGetter;
		this.databaseTravelDataGetter = dbGetter;
	}

	@Override
	public Set<Airport> getActiveAirports() {
		Set<Airport> airports = databaseTravelDataGetter.getAllActiveAirports();
		if (airports == null || airports.isEmpty()) {
			airports =apiClientTravelDataGetter.getAllActiveAirports();
		}
		return airports;
	}

	@Override
	public Airport getAirportByCode(String airportCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
