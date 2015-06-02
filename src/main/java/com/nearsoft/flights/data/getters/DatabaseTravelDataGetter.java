package com.nearsoft.flights.data.getters;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.nearsoft.flights.persistence.dao.AirportDao;
import com.nearsoft.flights.persistence.dao.FlightDao;
import com.nearsoft.flights.persistence.dto.TripInformationRequestDto;
import com.nearsoft.flights.vo.AirportDTO;
import com.nearsoft.flights.vo.Airports;
import com.nearsoft.flights.vo.FlightDto;
import com.nearsoft.flights.vo.Flights;

public class DatabaseTravelDataGetter implements TravelDataGetter, Observer {
	
	private AirportDao airportDAO;
	private FlightDao flightDAO;

	public DatabaseTravelDataGetter(AirportDao airportDAO, FlightDao flightDAO) {
		this.airportDAO = airportDAO;
		this.flightDAO = flightDAO;
	}
	
	@Override
	public Set<AirportDto> getAllActiveAirports() {
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
	public Set<FlightDto> getDepartingFlightsByRouteNDate(TripInformationRequestDto tripInformation) {
		return flightDAO.findDepartingFlightsByRouteNDate(tripInformation);
	}
}
