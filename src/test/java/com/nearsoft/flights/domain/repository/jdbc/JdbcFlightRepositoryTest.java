package com.nearsoft.flights.domain.repository.jdbc;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.nearsoft.flights.domain.model.Airline;
import com.nearsoft.flights.domain.model.Airport.AirportBuilder;
import com.nearsoft.flights.domain.model.Flight;
import com.nearsoft.flights.domain.model.Flight.FlightBuilder;
import com.nearsoft.flights.domain.model.ScheduledTrip;
import com.nearsoft.flights.domain.model.TripInformationRequest;
import com.nearsoft.flights.domain.repository.FlightRepository;
import com.nearsoft.flights.domain.repository.jdbc.specification.FlightSpecificationByTripInformation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/jdbc-test-config.xml")
public class JdbcFlightRepositoryTest {

	private static final Logger logger = Logger
			.getLogger(JdbcFlightRepositoryTest.class);
	
	public JdbcFlightRepositoryTest() {
		BasicConfigurator.configure();
	}
	
	@Autowired
	FlightRepository flightRepository;
	
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
	

	@Test
	public void shouldGetAllFlights() throws SQLException {
		logger.debug("Testing get all flights");
		int count = jdbcTemplate.queryForObject("select count(*) from flight", Integer.class);
		List<Flight> flights = flightRepository.getAll();
		logger.debug(flights);
		Assert.assertThat(flights.size(), is(count));
	}
	
	@Test
	public void shouldDeleteAllFlights() throws SQLException {
		logger.debug("Testing delete all flights");
		flightRepository.removeAll();
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from flight", Integer.class), is(0));
	}
	
	@Test
	public void shouldUpdateFlight() throws SQLException {
		logger.debug("Testing update flight");
		Flight flight = getFlight();
		String originalTerminal = flight.getDeparture().getTerminal();
		flightRepository.add(flight);
		int count = jdbcTemplate.queryForObject("select count(*) from flight", Integer.class);
		flight.modifyDepartureSchedule(flight.getDeparture().getScheduledDate(), "T3");
		flightRepository.update(flight);
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from flight", Integer.class), is(count));
		Flight flightModified = jdbcTemplate.query("select * from flight where flight_number='804' and airline_code='AIR1'", new ResultSetExtractor<Flight>(){
			@Override
			public Flight extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				rs.next();
				Airline airline = new Airline(rs.getString("airline_code"),null,null,null);
				FlightBuilder builder = new FlightBuilder(rs.getString("flight_number"),airline);
				AirportBuilder arrivalBuilder = new AirportBuilder(rs.getString("departure_airport_code"));
				AirportBuilder departureBuilder = new AirportBuilder(rs.getString("arrival_airport_code"));
				builder.addFlightRoute(new ScheduledTrip(departureBuilder.build(), 
						rs.getDate("departure_date"), 
						rs.getString("departure_terminal")), 
						new ScheduledTrip(arrivalBuilder.build(), 
								rs.getDate("arrival_date"),
								rs.getString("arrival_terminal")));
				builder.addServiceType(rs.getString("service_type"));
				return builder.build();
			}
		});
		
		Assert.assertNotNull(flightModified);
		Assert.assertThat(flightModified.getDeparture().getTerminal(), is(not(originalTerminal)));
	}
	
	@Test
	public void shouldDeleteFlight() throws SQLException {
		logger.debug("Testing delete flight");
		int count = jdbcTemplate.queryForObject("select count(*) from flight", Integer.class);
		FlightBuilder builder = new FlightBuilder("14578", new Airline("AIR1", null, null,null));
		Flight flight = builder.build();
		flightRepository.remove(flight);
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from flight", Integer.class), is(count-1));
		List<String> flights = jdbcTemplate.query("select airline_code from flight", (rs, rowNum) -> rs.getString(1));
		Assert.assertThat(flights, not(flights.contains(flight)));
	}
	
	
	@Test
	public void shouldAddAllFlights() throws SQLException {
		logger.debug("Testing add all flights");
		int count = jdbcTemplate.queryForObject("select count(*) from flight", Integer.class);
		Set<Flight> flights = getFlights(5);
		flightRepository.addAll(flights);
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from flight", Integer.class), is(count+5));
		
	}
	
	@Test
	public void shouldFindFlightBySpecification() {
		logger.debug("Testing find flight by specification");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2015);
		calendar.set(Calendar.MONTH, Calendar.JUNE);
		calendar.set(Calendar.DAY_OF_MONTH, 14);
		calendar.set(Calendar.HOUR_OF_DAY, 1); 
		List<Flight> flight = flightRepository.getBytTripInformation(new TripInformationRequest("1", calendar.getTime(), "3"));
		Assert.assertNotNull(flight);
		Assert.assertThat(flight, is(not(empty())));
		Assert.assertThat(flight.size(), is(1));
	}
	
	@Test
	public void shouldNotFindFlightBySpecification() {
		logger.debug("Testing find flight by specification");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2015);
		calendar.set(Calendar.MONTH, Calendar.JUNE);
		calendar.set(Calendar.DAY_OF_MONTH, 14);
		calendar.set(Calendar.HOUR_OF_DAY, 1); 
		List<Flight> flight = flightRepository.getBytTripInformation(new TripInformationRequest("1", calendar.getTime(), "2"));
		Assert.assertNotNull(flight);
		Assert.assertThat(flight, is(empty()));
		Assert.assertThat(flight.size(), is(0));
	}
	
	
	private Flight getFlight() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 5);
		FlightBuilder flightBuilder = new FlightBuilder("804",new Airline("AIR1", null, null, null));
		AirportBuilder arrivalBuilder = new AirportBuilder("1");
		AirportBuilder departureBuilder = new AirportBuilder("2");
		flightBuilder.addFlightRoute(new ScheduledTrip(departureBuilder.build(), new Date(), "T2"),
				new ScheduledTrip(arrivalBuilder.build(), new Date(calendar.getTimeInMillis()),"T1"));
		flightBuilder.addServiceType("K");
		return flightBuilder.build();
	}
	
	private Set<Flight> getFlights(int flightsCount) {
		Set<Flight> flights = new HashSet<>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 5);
		for(int i = 0 ;  i< flightsCount; i++ ){
			FlightBuilder flightBuilder = new FlightBuilder("804"+i,new Airline("AIR3", null, null, null));
			AirportBuilder arrivalBuilder = new AirportBuilder("1");
			AirportBuilder departureBuilder = new AirportBuilder("2");
			flightBuilder.addFlightRoute(new ScheduledTrip(departureBuilder.build(), new Date(), "T"+i),
					new ScheduledTrip(arrivalBuilder.build(), new Date(calendar.getTimeInMillis()),"T1"+i));
			flightBuilder.addServiceType("K");
			flights.add(flightBuilder.build());
		}
		return flights;
	}
}
