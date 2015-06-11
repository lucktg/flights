package com.nearsoft.flights.interfaces.flexapi.domain.model;

import java.util.List;

public class CodeSharePojo {
	private String carrierFsCode;
	private String flightNumber;
	private String serviceType;
	private List<String> serviceClasses;
	private List<String> trafficRestrictions;
	private String referenceCode;
	
	public CodeSharePojo() {
		
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

	public String getReferenceCode() {
		return referenceCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((carrierFsCode == null) ? 0 : carrierFsCode.hashCode());
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
		CodeSharePojo other = (CodeSharePojo) obj;
		if (carrierFsCode == null) {
			if (other.carrierFsCode != null)
				return false;
		} else if (!carrierFsCode.equals(other.carrierFsCode))
			return false;
		if (flightNumber == null) {
			if (other.flightNumber != null)
				return false;
		} else if (!flightNumber.equals(other.flightNumber))
			return false;
		return true;
	}
	
	
	
}