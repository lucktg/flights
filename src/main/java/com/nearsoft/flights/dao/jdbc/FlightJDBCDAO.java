package com.nearsoft.flights.dao.jdbc;

import java.util.Set;

import com.nearsoft.flights.dao.FlightDAO;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.TripInformation;

public class FlightJDBCDAO implements FlightDAO {

	@Override
	public void updateFlights(Set<Flight> flights) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Flight> findDepartingFlightsByRouteNDate(TripInformation tripInformation) {
		// TODO Auto-generated method stub
		return null;
	}

}
