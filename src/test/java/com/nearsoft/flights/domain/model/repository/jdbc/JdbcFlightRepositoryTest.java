package com.nearsoft.flights.domain.model.repository.jdbc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.domain.model.flight.Airline;
import com.nearsoft.flights.domain.model.flight.Flight;
import com.nearsoft.flights.domain.model.flight.Flight.FlightBuilder;
import com.nearsoft.flights.domain.model.flight.ScheduledTrip;
import com.nearsoft.flights.domain.model.repository.Repository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/jdbc-test-config.xml")
public class JdbcFlightRepositoryTest {

	private static final Logger logger = Logger
			.getLogger(JdbcFlightRepositoryTest.class);
	
	public JdbcFlightRepositoryTest() {
		BasicConfigurator.configure();
	}
	
	@Autowired
	Repository<Flight> flightRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	DataSource datasource;
	
	@Value("classpath:db/test-data.xml")
	Resource resource;
	
	@Before
	public void setup() throws SQLException, DatabaseUnitException, MalformedURLException, IOException {
		MockitoAnnotations.initMocks(this);
		
		IDataSet ds = new FlatXmlDataFileLoader().getBuilder().build(resource.getFile());
		IDatabaseConnection dbConn = new DatabaseDataSourceConnection(datasource);
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, ds);
		
	}
	
	@Test
	public void shouldInsertFlight() throws SQLException {
		logger.debug("Testing add flight repository");
		int count = jdbcTemplate.queryForObject("select count(1) from flight", Integer.class);
		flightRepository.add(getFlight());
		Assert.assertThat(jdbcTemplate.queryForObject("select count(1) from flight", Integer.class), 
				is(count+1));
	}
	

	
	public void shouldGetAllFlights() throws SQLException {
		logger.debug("Testing get all flights");
		int count = jdbcTemplate.queryForObject("select count(*) from flights", Integer.class);
		List<Flight> flights = flightRepository.getAll();
		logger.debug(flights);
		Assert.assertThat(flights.size(), is(count));
	}
	
	
	public void shouldDeleteAllFlights() throws SQLException {
		logger.debug("Testing delete all flights");
		flightRepository.removeAll();
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from flights", Integer.class), is(0));
	}
	
	
	public void shouldUpdateFlight() throws SQLException {
		logger.debug("Testing update flight");
		int count = jdbcTemplate.queryForObject("select count(*) from flight", Integer.class);
		flightRepository.update(getFlight());
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from flight", Integer.class), is(count));
		Flight flight = jdbcTemplate.query("select * from flight where flight_number='804' and airline_code='AA1'", new ResultSetExtractor<Flight>(){
			@Override
			public Flight extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				rs.next();
				Airline airline = new Airline(rs.getString("airline_code"),null,null,null);
				FlightBuilder builder = new FlightBuilder(rs.getString("flight_number"),airline);
				AirportBuilder arrivalBuilder = new AirportBuilder(rs.getString("departure_airport_code"));
				AirportBuilder departureBuilder = new AirportBuilder(rs.getString("arrival_airport_code"));
				builder.addArrival(new ScheduledTrip(arrivalBuilder.build(), 
						rs.getDate("arrival_date"),
						rs.getString("arrival_terminal")));
				builder.addDeparture(new ScheduledTrip(departureBuilder.build(), 
						rs.getDate("departure_date"), 
						rs.getString("departure_terminal")));
				builder.addServiceType(rs.getString("service_type"));
				return builder.build();
			}
			
		});
		
		
	}
	
	
	public void shouldDeleteFlight() throws SQLException {
		logger.debug("Testing delete flight");
		int count = jdbcTemplate.queryForObject("select count(*) from flight", Integer.class);
		Flight flight = getFlight();
		flightRepository.remove(flight);
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from flight", Integer.class), is(count-1));
		List<String> flights = jdbcTemplate.query("select airline_code from flight", (rs, rowNum) -> rs.getString(1));
		Assert.assertThat(flights, not(flights.contains(flight)));
	}
	
	private Flight getFlight() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 5);
		FlightBuilder flightBuilder = new FlightBuilder("804",new Airline("AA1", null, "Airline testland ", null));
		AirportBuilder arrivalBuilder = new AirportBuilder("AAA")
			.addAirportName("Arrival airport 1")
			.addCity("City arrival 1")
			.addCityCode("ARRCT1")
			.addCountryCode("ARRCY1")
			.addCountryName("Arrival country1")
			.addLatitude("123456")
			.addLongitude("7654321");
		AirportBuilder departureBuilder = new AirportBuilder("DDD")
			.addAirportName("Departure airport 1")
			.addCity("City departure 1")
			.addCityCode("DEPCT1")
			.addCountryCode("DEPCY1")
			.addCountryName("Departure country1")
			.addLatitude("09876")
			.addLongitude("67890");
		flightBuilder.addArrival(new ScheduledTrip(arrivalBuilder.build(), new Date(calendar.getTimeInMillis()),"T1"));
		flightBuilder.addDeparture(new ScheduledTrip(departureBuilder.build(), new Date(), "T2"));
		flightBuilder.addServiceType("K");
		return flightBuilder.build();
	}
}
