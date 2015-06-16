package com.nearsoft.flights.domain.repository.jdbc;

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

import com.nearsoft.flights.domain.model.Airline;
import com.nearsoft.flights.domain.repository.AirlineRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/jdbc-test-config.xml")
public class JdbcAirlineRepositoryTest {
	
	private static final Logger logger = Logger
			.getLogger(JdbcAirlineRepositoryTest.class);

	@Autowired
	AirlineRepository airlineRepository;
	
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
	
	public JdbcAirlineRepositoryTest() {
		BasicConfigurator.configure();
	}
	
	@Test
	public void shouldInsertAirline() throws SQLException {
		logger.debug("Testing Add Airline Repository");
		int count = jdbcTemplate.queryForObject("select count(1) from Airline ", Integer.class);
		airlineRepository.add(getAirline());
		Assert.assertThat(jdbcTemplate.queryForObject("select count(1) from Airline ", Integer.class), 
				is(count+1));
	}
	
	@Test
	public void shouldGetAllAirlines() throws SQLException {
		logger.debug("Testing getAll ailines");
		int count = jdbcTemplate.queryForObject("select count(*) from Airline", Integer.class);
		List<Airline> airlines = airlineRepository.getAll();
		logger.debug(airlines);
		Assert.assertThat(airlines.size(), is(count));
	}
	
	@Test
	public void shouldDeleteAllAirlines() throws SQLException {
		logger.debug("Testing delete All Airlines");
		airlineRepository.removeAll();
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from Airline", Integer.class), is(0));
	}
	
	@Test
	public void shouldUpdateAirline() throws SQLException {
		logger.debug("Testing update Airline");
		int count = jdbcTemplate.queryForObject("select count(*) from airline ", Integer.class);
		airlineRepository.update(new Airline("AIR1",null, "Airline number one", null));
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from Airline", Integer.class), is(count));
		Airline airline = jdbcTemplate.query("select * from airline where airline_code='AIR1'", new ResultSetExtractor<Airline>(){
			@Override
			public Airline extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				rs.next();
				return new Airline(rs.getString("airline_code"), rs.getString("phone_number"),rs.getString("airline_name"), null);
			}
			
		});
		Assert.assertNull(airline.getPhoneNumber());
		Assert.assertThat(airline.getAirlineName(), is("Airline number one"));
		
	}
	
	@Test
	public void shouldDeleteAirlines() throws SQLException {
		logger.debug("Testing delete airline");
		int count = jdbcTemplate.queryForObject("select count(*) from Airline", Integer.class);
		Airline airline = new Airline("AIR3", null, null, null);
		airlineRepository.remove(airline);
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from airline", Integer.class), is(count-1));
		List<String> airportCodes = jdbcTemplate.query("select airline_code from airline", (rs, rowNum) -> rs.getString(1));
		Assert.assertThat(airportCodes, not(airportCodes.contains(airline)));
	}
	
	@Test
	public void shouldAddAllAirlines() throws SQLException {
		logger.debug("Testing add all airlines");
		int count = jdbcTemplate.queryForObject("select count(*) from airline", Integer.class);
		Set<Airline> airlines = getAirlines(5);
		airlineRepository.addAll(airlines);
		Assert.assertThat(jdbcTemplate.queryForObject("select count(*) from airline", Integer.class), is(count+5));
		
	}
	
	private Airline getAirline() {
		return new Airline("AA1", null, "Airline testland ", null);
	}
	
	private Set<Airline> getAirlines(int airlinesNum) {
		Set<Airline> airlines = new HashSet<>();
		for (int i = 0; i < airlinesNum; i++) {
			airlines.add(new Airline("AA"+i, null, "Airline testland ", null));
		}
		return airlines;
	}
}
