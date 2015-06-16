package com.nearsoft.flights.domain.repository.jdbc.specification;

import java.util.regex.Pattern;


public class AirportSpecificationByCode implements SqlSpecification {
	private String airportCode;
	private static final Pattern pattern = Pattern.compile("\\p{Alnum}*");
	public AirportSpecificationByCode(String airportCode) {
		if(!pattern.matcher(airportCode).matches()) throw new AssertionError("Airport code must be Alphanumeric");
		this.airportCode = airportCode;
	}

	@Override
	public String toSqlClauses() {
		return String.format(" where airport_code = '%s'", airportCode);
	}

}
