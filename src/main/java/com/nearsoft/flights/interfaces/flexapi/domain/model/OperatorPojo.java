package com.nearsoft.flights.interfaces.flexapi.domain.model;

import java.util.List;

public class OperatorPojo {

	private String carrierFsCode;
	private String flightNumber;
	private String serviceType;
	private List<String> serviceClasses;
	private List<String> trafficRestrictions;
	
	public OperatorPojo() {
		
	}

	public String getCarrierFsCode() {
		return carrierFsCode;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public String getServiceType() {
		return serviceType;
	}

	public List<String> getServiceClasses() {
		return serviceClasses;
	}

	public List<String> getTrafficRestrictions() {
		return trafficRestrictions;
	}
	
	
}
