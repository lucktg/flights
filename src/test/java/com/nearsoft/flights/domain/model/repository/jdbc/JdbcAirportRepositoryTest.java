package com.nearsoft.flights.domain.model.repository.jdbc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.domain.model.flight.Airline;
import com.nearsoft.flights.domain.model.repository.Repository;
import com.nearsoft.flights.domain.model.repository.jdbc.specification.AirportSpecificationByCode;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/jdbc-test-config.xml")
public class JdbcAirportRepositoryTest {
	
	private static final Logger logger = Logger
			.getLogger(JdbcAirportRepositoryTest.class);

	@Autowired
	Repository<Airport> airportRepository;
	
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
		
		jdbcTemplate.update("DELETE FROM FLIGHT");
		
	}
	
	public JdbcAirportRepositoryTest() {
		BasicConfigurator.configure();
	}
	
	@Test
	public void shouldInsertAirport() throws SQLException {
		logger.debug("Testing Add Airport Respository");
		int count = jdbcTemplate.queryForObject("select count(1) from Airport ", Integer.class);
		airportRepository.add(getAirport());
		Assert.assertThat(jdbcTemplate.queryForObject("select count(1) from Airport ", Integer.class), 
				is(count+1));
	}
	
	@Test
	public void shouldGetAllAirports() throws SQLException {
		logger.debug("Testing getAll airports");
		int count = jdbcTemplate.queryForObject("select count(*) from Airport", Integer.class);
		List<Airport> airports = airportRepository.getAll();
		logger.debug(airports);
		Assert.assertThat(airports.size(), is(count));
	}
	
	@Test
	public void shouldDeleteAllAirports() throws SQLException {
		logger.debug("Testing delete All airports");
		airportRepository.removeAll();
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from Airport", Integer.class), is(0));
	}
	
	@Test
	public void shouldDeleteAirport() throws SQLException {
		logger.debug("Testing delete airports");
		int count = jdbcTemplate.queryForObject("select count(*) from Airport", Integer.class);
		AirportBuilder builder = new AirportBuilder("2");
		airportRepository.remove(builder.build());
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from Airport", Integer.class), is(count-1));
		List<String> airportCodes = jdbcTemplate.query("select airport_code from airport", (rs, rowNum) -> rs.getString(1));
		Assert.assertThat(airportCodes, not(airportCodes.contains(builder)));
	}
	
	@Test
	public void shouldAddAllAirports() throws SQLException {
		logger.debug("Testing add all airports");
		int count = jdbcTemplate.queryForObject("select count(*) from airport", Integer.class);
		Set<Airport> airports = getAirports(5);
		airportRepository.addAll(airports);
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from airport", Integer.class), is(count+5));
		
	}
	
	@Test
	public void shouldUpdateAirport() throws SQLException {
		logger.debug("Testing update airport");
		int count = jdbcTemplate.queryForObject("select count(*) from airport", Integer.class);
		AirportBuilder builder = new AirportBuilder("1");
		builder.addCity("City X");
		builder.addCityCode("CD?");
		builder.addCountryCode("MX");
		builder.addCountryName("Mexico");
		builder.addLatitude("1");
		builder.addLongitude("2");
		builder.addAirportName("Airport from city X");
		builder.addStateCode("ST?");
		Airport airportToUpdate = builder.build();
		airportRepository.update(airportToUpdate);
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from airport", Integer.class), is(count));
		Airport airport = jdbcTemplate.query("select * from airport where airport_code='1'", new ResultSetExtractor<Airport>(){
			@Override
			public Airport extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				rs.next();
				AirportBuilder builder = new AirportBuilder(rs.getString("airport_code"));
				builder.addCity(rs.getString("city"));
				builder.addCityCode(rs.getString("city_code"));
				return builder.build();
			}
		});
		Assert.assertNotNull(airport.getCity());
		Assert.assertThat(airportToUpdate.getCity(), is("City X"));
		
	}
	
	@Test
	public void shouldfindAirportByCodeSpecification() {
		logger.debug("Testing find Airport by code specification");
		Airport airport = airportRepository.getBySpecification(new AirportSpecificationByCode("3"));
		Assert.assertNotNull(airport);
		Assert.assertThat(airport.getAirportName(), is("airport name test 3"));
	}
	
	private Airport getAirport() {
		AirportBuilder builder = new AirportBuilder("??");
		builder.addCity("City X");
		builder.addCityCode("CD?");
		builder.addCountryCode("MX");
		builder.addCountryName("Mexico");
		builder.addLatitude("1");
		builder.addLongitude("2");
		builder.addAirportName("Airport from city X");
		builder.addStateCode("ST?");
		return builder.build();
	}
	
	private Set<Airport> getAirports(int airportsNumber) {
		Set<Airport> airports = new HashSet<>();
		for (int i = 0; i < airportsNumber; i++) {
			AirportBuilder builder = new AirportBuilder("A" +i);
			builder.addCity("City X");
			builder.addCityCode("CD?");
			builder.addCountryCode("MX");
			builder.addCountryName("Mexico");
			builder.addLatitude("1"+i);
			builder.addLongitude("2"+i);
			builder.addAirportName("Airport from city X");
			builder.addStateCode("ST?");
			airports.add(builder.build());
		}
		return airports;
	}
}
