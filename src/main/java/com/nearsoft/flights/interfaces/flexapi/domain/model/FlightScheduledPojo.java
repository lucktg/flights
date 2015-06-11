package com.nearsoft.flights.interfaces.flexapi.domain.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class FlightScheduledPojo {
	private String carrierFsCode;
	private String flightNumber;
	private String departureAirportFsCode;
	private String arrivalAirportFsCode;
	private String stops;
	private String arrivalTerminal;
	private String departureTerminal;
	private Date departureTime;	
	private Date arrivalTime;
	private String flightEquipmentIataCode;
	private String isCodeshare;
	private String isWetlease;
	private String serviceType;
	private Set<String> serviceClasses;
	private List<String> trafficRestrictions;
	private List<CodeSharePojo> codeshares;
	private OperatorPojo operator;
	private String wetleaseOperatorFsCode;
	private String referenceCode;
	
	
	public FlightScheduledPojo() {
		
	}

	public String getCarrierFsCode() {
		return carrierFsCode;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public String getDepartureAirportFsCode() {
		return departureAirportFsCode;
	}

	public String getArrivalAirportFsCode() {
		return arrivalAirportFsCode;
	}

	public String getStops() {
		return stops;
	}

	public String getArrivalTerminal() {
		return arrivalTerminal;
	}

	public String getDepartureTerminal() {
		return departureTerminal;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public String getFlightEquipmentIataCode() {
		return flightEquipmentIataCode;
	}

	public String getIsCodeshare() {
		return isCodeshare;
	}

	public String getIsWetlease() {
		return isWetlease;
	}

	public String getServiceType() {
		return serviceType;
	}

	public Set<String> getServiceClasses() {
		return serviceClasses;
	}

	public List<String> getTrafficRestrictions() {
		return trafficRestrictions;
	}
	
	public OperatorPojo getOperator() {
		return operator;
	}

	public List<CodeSharePojo> getCodeshares() {
		return codeshares;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((flightNumber == null) ? 0 : flightNumber.hashCode());
		result = prime * result
				+ ((referenceCode == null) ? 0 : referenceCode.hashCode());
		return result;
	}
	
	public String getWetleaseOperatorFsCode() {
		return wetleaseOperatorFsCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightScheduledPojo other = (FlightScheduledPojo) obj;
		if (flightNumber == null) {
			if (other.flightNumber != null)
				return false;
		} else if (!flightNumber.equals(other.flightNumber))
			return false;
		if (referenceCode == null) {
			if (other.referenceCode != null)
				return false;
		} else if (!referenceCode.equals(other.referenceCode))
			return false;
		return true;
	}
	
	
	
	
}