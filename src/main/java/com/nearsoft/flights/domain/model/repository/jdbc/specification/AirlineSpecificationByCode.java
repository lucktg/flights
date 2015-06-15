package com.nearsoft.flights.domain.model.repository.jdbc.specification;


public class AirlineSpecificationByCode implements SqlSpecification {

	private String airlineCode;
	
	
	public AirlineSpecificationByCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}


	@Override
	public String toSqlClauses() {
		return String.format(" where airline_code = '%s'", airlineCode);
	}

}
