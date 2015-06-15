package com.nearsoft.flights.domain.model.flight;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.repository.jdbc.ForeignKey;
import com.nearsoft.flights.domain.model.repository.jdbc.IgnorePersistence;
import com.nearsoft.flights.domain.model.repository.jdbc.Table;
import com.nearsoft.flights.domain.model.repository.jdbc.IgnorePersistence.Operation;

@Table(tableName="Flight", idTable={"airlineCode", "flightNumber"})
public class Flight {
	@IgnorePersistence(ignore=Operation.ALL)
	private static final Flight EMPTY_FLIGHT = new Flight();
	
	@IgnorePersistence(ignore=Operation.UPDATE)
	private String flightNumber;
	
	@IgnorePersistence(ignore=Operation.ALL)
	private Set<Flight> connectionFlights= Collections.emptySet();
	
	@IgnorePersistence(ignore=Operation.UPDATE)
	@ForeignKey
	private Airline airline;
	
	@IgnorePersistence(ignore=Operation.UPDATE)
	@ForeignKey(columns="departure_airport_code")
	private Airport departureAirport;
	private Date departureDate;
	private String departureTerminal;
	
	@IgnorePersistence(ignore=Operation.UPDATE)
	@ForeignKey(columns="arrival_airport_code")
	private Airport arrivalAirport;
	private Date arrivalDate;
	private String arrivalTerminal;
	
	@IgnorePersistence(ignore=Operation.ALL)
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
		return new ScheduledTrip(departureAirport, departureDate, departureTerminal);
	}
	
	public String getFlightNumber() {
		return flightNumber;
	}
	
	
	public ScheduledTrip getArrival() {
		return new ScheduledTrip(arrivalAirport, arrivalDate, arrivalTerminal);
	}
	
	public void modifyDepartureSchedule(Date date, String departureTerminal) {
		this.departureDate = date;
		this.departureTerminal = departureTerminal;
	}
	
	public void modifyArrivalSchedule(Date date, String arrivalTerminal) {
		this.arrivalDate = date;
		this.arrivalTerminal = arrivalTerminal;
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
		private Set<Flight> connectionFlights = Collections.emptySet();
		private Airline airline;
		private Airport departureAirport;
		private Date departureDate;
		private String departureTerminal;
		private Airport arrivalAirport;
		private Date arrivalDate;
		private String arrivalTerminal;
		private Set<String> serviceClasses;
		private String serviceType;
		
		public FlightBuilder(String flightNumber, Airline airline) {
			this.flightNumber = flightNumber;
			this.airline = airline;
		}
		
		public FlightBuilder addConnectionFlights(Set<Flight> connectionFlights){
			this.connectionFlights = connectionFlights;
			return this;
		}

		public FlightBuilder addFlightRoute(ScheduledTrip departure, ScheduledTrip arrival){
			if(departure.getAirport().equals(arrival.getAirport())) throw new IllegalArgumentException("Arrival and departure airport are the same");
			if(departure.getScheduledDate().after(arrival.getScheduledDate())) throw new IllegalArgumentException("Departure date is after arrival date");
			this.departureAirport = departure.getAirport();
			this.departureDate = departure.getScheduledDate();
			this.departureTerminal = departure.getTerminal();
			this.arrivalAirport = arrival.getAirport();
			this.arrivalDate = arrival.getScheduledDate();
			this.arrivalTerminal = arrival.getTerminal();
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
			flight.arrivalAirport = this.arrivalAirport;
			flight.arrivalDate = this.arrivalDate;
			flight.arrivalTerminal = this.arrivalTerminal;
			flight.departureAirport = this.departureAirport;
			flight.departureDate = this.departureDate;
			flight.departureTerminal = this.departureTerminal;
			flight.connectionFlights = this.connectionFlights;
			flight.serviceClasses = this.serviceClasses;
			flight.serviceType = this.serviceType;
			return flight;
		}
		
	}



	@Override
	public String toString() {
		return new StringBuilder("Flight [flightNumber=")
			.append(flightNumber)
			.append(", connectionFlights=")
			.append(connectionFlights)
			.append(", airline=")
			.append(airline)
			.append(", departureAirport=")
			.append(departureAirport)
			.append(", departureDate=")
			.append(departureDate)
			.append(", departureTerminal=")
			.append(departureTerminal)
			.append(", arrivalAirport=")
			.append(arrivalAirport)
			.append(", arrivalDate=")
			.append(arrivalDate)
			.append(", arrivalTerminal=")
			.append(arrivalTerminal)
			.append(", serviceClasses=")
			.append(serviceClasses)
			.append(", serviceType=")
			.append(serviceType)
			.append("]").toString();
	}
}
