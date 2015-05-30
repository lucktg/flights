package com.nearsoft.flights.persistence.dao.jdbc;

import java.util.Set;

import com.nearsoft.flights.persistence.dao.FlightDAO;
import com.nearsoft.flights.vo.Flight;
import com.nearsoft.flights.vo.TripInformationRequest;

public class FlightJDBCDAO implements FlightDAO {

	@Override
	public void updateFlights(Set<Flight> flights) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Flight> findDepartingFlightsByRouteNDate(TripInformationRequest tripInformation) {
		// TODO Auto-generated method stub
		return null;
	}

}
