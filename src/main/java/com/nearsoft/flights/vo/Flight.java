package com.nearsoft.flights.vo;

import java.util.Date;

public class Flight implements FlightItem {
	
	private String carrierFSCode;
	private String flightNumber;
	private String departureAirportFSCode;
	private String arrivalAirportFSCode;
	private String stops;
	private String arrivalTerminal;
	private Date  departureTime;
	private Date arrivalTime;
	
	public Flight () {
		
	}
	
	public String getCarrierFSCode() {
		return carrierFSCode;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public String getDepartureAirportFSCode() {
		return departureAirportFSCode;
	}
	public String getArrivalAirportFSCode() {
		return arrivalAirportFSCode;
	}
	public String getStops() {
		return stops;
	}
	public String getArrivalTerminal() {
		return arrivalTerminal;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	
}
