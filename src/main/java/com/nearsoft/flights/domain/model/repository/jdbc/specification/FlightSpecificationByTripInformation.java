package com.nearsoft.flights.domain.model.repository.jdbc.specification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nearsoft.flights.domain.model.flight.TripInformationRequest;

public class FlightSpecificationByTripInformation implements SqlSpecification{

	private TripInformationRequest tripInfo;
	
	public FlightSpecificationByTripInformation(TripInformationRequest tripInfo) {
		this.tripInfo = tripInfo;
	}
	
	@Override
	public String toSqlClauses() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tripInfo.getDepartureDate().getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return String.format(" where departure_airport_code = '%s' and arrival_airport_code = '%s' and departure_date between (TIMESTAMP '%s') and (TIMESTAMP '%s')", 
				tripInfo.getDepartureAirportCode(), 
				tripInfo.getArrivalAirportCode(), 
				format.format(tripInfo.getDepartureDate()), 
				format.format(calendar.getTime()));
	}
	
	
	public static void main(String[] args) {
		FlightSpecificationByTripInformation specification = new FlightSpecificationByTripInformation(new TripInformationRequest("1", new Date(), "2"));
		System.out.println(specification.toSqlClauses());
	}
}
