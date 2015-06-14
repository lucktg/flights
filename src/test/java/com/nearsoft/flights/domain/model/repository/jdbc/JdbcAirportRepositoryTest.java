package com.nearsoft.flights.domain.model.repository.jdbc;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nearsoft.flights.domain.model.airport.Airport;
import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.domain.model.repository.Repository;


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
}
