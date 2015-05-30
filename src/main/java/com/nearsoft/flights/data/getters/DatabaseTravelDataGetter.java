package com.nearsoft.flights.data.getters;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.nearsoft.flights.persistence.dao.AirportDAO;
import com.nearsoft.flights.persistence.dao.FlightDAO;
import com.nearsoft.flights.vo.AirportDTO;
import com.nearsoft.flights.vo.Airports;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.Flights;
import com.nearsoft.flights.vo.TripInformationRequest;

public class DatabaseTravelDataGetter implements TravelDataGetter, Observer {
	
	private AirportDAO airportDAO;
	private FlightDAO flightDAO;

	public DatabaseTravelDataGetter(AirportDAO airportDAO, FlightDAO flightDAO) {
		this.airportDAO = airportDAO;
		this.flightDAO = flightDAO;
	}
	
	@Override
	public Set<AirportDTO> getAllActiveAirports() {
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
	public Set<Flight> getDepartingFlightsByRouteNDate(TripInformationRequest tripInformation) {
		return flightDAO.findDepartingFlightsByRouteNDate(tripInformation);
	}
}
