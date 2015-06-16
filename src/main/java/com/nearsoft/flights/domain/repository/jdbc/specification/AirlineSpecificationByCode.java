package com.nearsoft.flights.domain.repository.jdbc.specification;

import java.util.regex.Pattern;


public class AirlineSpecificationByCode implements SqlSpecification {

	private String airlineCode;
	
	private static final Pattern pattern = Pattern.compile("\\p{Alnum}*");
	
	public AirlineSpecificationByCode(String airlineCode) {
		if(!pattern.matcher(airlineCode).matches()) throw new AssertionError("Airline code must be Alphanumeric");
		this.airlineCode = airlineCode;
	}


	@Override
	public String toSqlClauses() {
		return String.format(" where airline_code = '%s'", airlineCode);
	}

}
