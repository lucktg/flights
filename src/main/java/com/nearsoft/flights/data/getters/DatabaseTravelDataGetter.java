package com.nearsoft.flights.data.getters;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.nearsoft.flights.dao.AirportDAO;
import com.nearsoft.flights.dao.FlightDAO;
import com.nearsoft.flights.vo.Airport;
import com.nearsoft.flights.vo.Airports;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.Flights;
import com.nearsoft.flights.vo.TripInformation;

public class DatabaseTravelDataGetter implements TravelDataGetter, Observer {
	
	private AirportDAO airportDAO;
	private FlightDAO flightDAO;

	public DatabaseTravelDataGetter(AirportDAO airportDAO, FlightDAO flightDAO) {
		this.airportDAO = airportDAO;
		this.flightDAO = flightDAO;
	}
	
	@Override
	public Set<Airport> getAllActiveAirports() {
		return airportDAO.findActiveAirports();
	}

	@Override
	public void update(Observable observable, Object travelItem) {
		if (travelItem == null) {
			return;
		} else if (travelItem instanceof Airports) {
			airportDAO.updateAirports(((Airports) travelItem).getItems());
		} else if (travelItem instanceof Flights) {
			flightDAO.updateFlights(((Flights) travelItem).getItems());
		} else {
			throw new RuntimeException("No such flight item");
		}
	}

	@Override
	public Set<Flight> getDepartingFlightsByRouteNDate(TripInformation tripInformation) {
		return flightDAO.findDepartingFlightsByRouteNDate(tripInformation);
	}
}