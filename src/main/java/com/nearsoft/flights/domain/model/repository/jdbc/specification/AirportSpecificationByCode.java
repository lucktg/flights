package com.nearsoft.flights.domain.model.repository.jdbc.specification;


public class AirportSpecificationByCode implements SqlSpecification {
	private String airportCode;
	
	public AirportSpecificationByCode(String airportCode) {
		this.airportCode = airportCode;
	}

	@Override
	public String toSqlClauses() {
		return String.format(" where airport_code = '%s'", airportCode);
	}

}
