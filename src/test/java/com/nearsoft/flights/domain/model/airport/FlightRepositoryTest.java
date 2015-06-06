package com.nearsoft.flights.domain.model.airport;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Statistics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nearsoft.flights.domain.model.flight.FlightService;
import com.nearsoft.flights.domain.model.flight.TripInformationRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class FlightRepositoryTest {

	@Autowired
	private FlightService flightRepository;
	
	@Autowired
    private CacheManager cacheManager;
	
	private Cache flightCache;
	
	@Before
    public void before() throws Exception {
		MockitoAnnotations.initMocks(this); 
        cacheManager.clearAll();
        flightCache = cacheManager.getCache("flights");
		flightCache.setStatisticsEnabled(true);
		flightCache.setStatisticsAccuracy(Statistics.STATISTICS_ACCURACY_GUARANTEED);
    }
	@Test
	public void shouldGetAirportsFromCache() throws Exception {
		flightRepository.findDepartingFlightsByTripInformationRequest(getTripInformationRequestHMO());
		Assert.assertEquals(1, flightCache.getStatistics().getMemoryStoreObjectCount());
		Assert.assertEquals(0, flightCache.getStatistics().getCacheHits());
		Assert.assertEquals(1, flightCache.getStatistics().getCacheMisses());
		flightRepository.findDepartingFlightsByTripInformationRequest(getTripInformationRequestHMO());
		Assert.assertEquals(1, flightCache.getStatistics().getMemoryStoreObjectCount());
		Assert.assertEquals(1, flightCache.getStatistics().getCacheHits());
		Assert.assertEquals(1, flightCache.getStatistics().getCacheMisses());
		flightRepository.findDepartingFlightsByTripInformationRequest(getTripInformationRequestHMO());
		Assert.assertEquals(1, flightCache.getStatistics().getMemoryStoreObjectCount());
		Assert.assertEquals(2, flightCache.getStatistics().getCacheHits());
		Assert.assertEquals(1, flightCache.getStatistics().getCacheMisses());
		
		//Al cambiar la informacion de busqueda, se agrega un nuevo elemento en el cache
		flightRepository.findDepartingFlightsByTripInformationRequest(getTripInformationRequestGDL());
		Assert.assertEquals(2, flightCache.getStatistics().getMemoryStoreObjectCount());
		Assert.assertEquals(2, flightCache.getStatistics().getCacheHits());
		Assert.assertEquals(2, flightCache.getStatistics().getCacheMisses());
		flightRepository.findDepartingFlightsByTripInformationRequest(getTripInformationRequestGDL());
		Assert.assertEquals(2, flightCache.getStatistics().getMemoryStoreObjectCount());
		Assert.assertEquals(3, flightCache.getStatistics().getCacheHits());
		Assert.assertEquals(2, flightCache.getStatistics().getCacheMisses());
	}
	
	private TripInformationRequest getTripInformationRequestHMO() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse("2015-06-04");
		return new TripInformationRequest("MEX",date,"HMO");
	}
	private TripInformationRequest getTripInformationRequestGDL() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse("2015-06-04");
		return new TripInformationRequest("MEX",date,"GDL");
	}
}
