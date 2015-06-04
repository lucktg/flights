package com.nearsoft.flights.domain.model.flight;

import java.util.LinkedHashSet;
import java.util.Set;

public class Flight {
	
	private static final Flight EMPTY_FLIGHT = new Flight();
	
	private String flightNumber;
	//TODO replace with a Set and initialize with an empty Set
	private LinkedHashSet<Flight> connectionFlights;
	private Airline airline;
	private ScheduledTrip departure;
	private ScheduledTrip arrival;
	private Set<String> serviceClasses;
	private String serviceType;
	
	private Flight() {
		
	}
	
	private Flight(String flightNumber, Airline airline) {
		this.flightNumber = flightNumber;
		this.airline = airline;
	}
	
	public Set<String> getServiceClass() {
		return serviceClasses;
	}
	
	public String getServiceType() {
		return serviceType;
	}
	
	public Airline getAirline() {
		return airline;
	}
	
	public ScheduledTrip getDeparture() {
		return departure;
	}
	
	public String getFlightNumber() {
		return flightNumber;
	}
	
	
	public ScheduledTrip getArrival() {
		return arrival;
	}
	
	public boolean hasStops(){
		return !(connectionFlights != null && connectionFlights.isEmpty());
	}
	
	public void addConnectingFlight(Flight flight){
		connectionFlights.add(flight);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airline == null) ? 0 : airline.hashCode());
		result = prime * result
				+ ((flightNumber == null) ? 0 : flightNumber.hashCode());
		return result;
	}
	
	public static Flight emptyFlight() {
		return EMPTY_FLIGHT;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (airline == null) {
			if (other.airline != null)
				return false;
		} else if (!airline.equals(other.airline))
			return false;
		if (flightNumber == null) {
			if (other.flightNumber != null)
				return false;
		} else if (!flightNumber.equals(other.flightNumber))
			return false;
		return true;
	}
	
	public static class FlightBuilder {
		private String flightNumber;
		private LinkedHashSet<Flight> connectionFlights;
		private Airline airline;
		private ScheduledTrip departure;
		private ScheduledTrip arrival;
		private Set<String> serviceClasses;
		private String serviceType;
		
		public FlightBuilder(String flightNumber, Airline airline) {
			this.flightNumber = flightNumber;
			this.airline = airline;
		}
		
		public FlightBuilder addConnectionFlights(LinkedHashSet<Flight> connectionFlights){
			this.connectionFlights = connectionFlights;
			return this;
		}

		public FlightBuilder addDeparture(ScheduledTrip departure){
			this.departure = departure;
			return this;
		}
		public FlightBuilder addArrival(ScheduledTrip arrival){
			this.arrival = arrival;
			return this;
		}
		public FlightBuilder addServiceClasses(Set<String> serviceClasses){
			this.serviceClasses = serviceClasses;
			return this;
		}
		public FlightBuilder addServiceType(String serviceType){
			this.serviceType = serviceType;
			return this;
		}
		
		public Flight build() {
			Flight flight = new Flight(this.flightNumber, this.airline);
			flight.arrival = this.arrival;
			flight.connectionFlights = this.connectionFlights;
			flight.departure = this.departure;
			flight.serviceClasses = this.serviceClasses;
			flight.serviceType = this.serviceType;
			return flight;
		}
		
	}
}
