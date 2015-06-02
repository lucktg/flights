package com.nearsoft.flights.persistence.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class FlightDto implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7090692690829040890L;
	
	private String flightNumber;
	private AirlineDto airline;
	private Timestamp departureDate;
	private String departureTerminal;
	private AirportDto departureAirport;
	private Timestamp arrivalDate;
	private String arrivalTerminal;
	private AirportDto arrivalAirport;
	private String serviceType;
	
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public Timestamp getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Timestamp departureDate) {
		this.departureDate = departureDate;
	}
	public String getDepartureTerminal() {
		return departureTerminal;
	}
	public void setDepartureTerminal(String departureTerminal) {
		this.departureTerminal = departureTerminal;
	}
	public Timestamp getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Timestamp arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getArrivalTerminal() {
		return arrivalTerminal;
	}
	public void setArrivalTerminal(String arrivalTerminal) {
		this.arrivalTerminal = arrivalTerminal;
	}
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public AirlineDto getAirline() {
		return airline;
	}
	public void setAirline(AirlineDto airline) {
		this.airline = airline;
	}
	public AirportDto getDepartureAirport() {
		return departureAirport;
	}
	public void setDepartureAirport(AirportDto departureAirport) {
		this.departureAirport = departureAirport;
	}
	public AirportDto getArrivalAirport() {
		return arrivalAirport;
	}
	public void setArrivalAirport(AirportDto arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airline == null) ? 0 : airline.hashCode());
		result = prime
				* result
				+ ((departureAirport == null) ? 0 : departureAirport.hashCode());
		result = prime * result
				+ ((flightNumber == null) ? 0 : flightNumber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightDto other = (FlightDto) obj;
		if (airline == null) {
			if (other.airline != null)
				return false;
		} else if (!airline.equals(other.airline))
			return false;
		if (departureAirport == null) {
			if (other.departureAirport != null)
				return false;
		} else if (!departureAirport.equals(other.departureAirport))
			return false;
		if (flightNumber == null) {
			if (other.flightNumber != null)
				return false;
		} else if (!flightNumber.equals(other.flightNumber))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FlightDto [flightNumber=" + flightNumber + ", airline="
				+ airline + ", departureDate=" + departureDate
				+ ", departureTerminal=" + departureTerminal
				+ ", departureAirport=" + departureAirport + ", arrivalDate="
				+ arrivalDate + ", arrivalTerminal=" + arrivalTerminal
				+ ", arrivalAirport=" + arrivalAirport + ", serviceType="
				+ serviceType + "]";
	}
	
	
	
}
