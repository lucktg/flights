package com.nearsoft.flights.domain.model.airport;

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

import com.nearsoft.flights.domain.model.exception.RepositoryException;
import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class AirportRepositoryTest {

	@Autowired
	private AirportRepository airportRepository;
	
	@Autowired
    private CacheManager cacheManager;
	
	private Cache airportCache;
	
	@Before
    public void before() throws Exception {
		MockitoAnnotations.initMocks(this); 
        cacheManager.clearAll();
        airportCache = cacheManager.getCache("airports");
		airportCache.setStatisticsEnabled(true);
		airportCache.setStatisticsAccuracy(Statistics.STATISTICS_ACCURACY_GUARANTEED);
    }
	@Test
	public void shouldGetAirportsFromCache() throws RepositoryException, PersistenceException{
		airportRepository.findAllActiveAirports();
		Assert.assertEquals(1, airportCache.getStatistics().getMemoryStoreObjectCount());
		Assert.assertEquals(0, airportCache.getStatistics().getCacheHits());
		Assert.assertEquals(1, airportCache.getStatistics().getCacheMisses());
		airportRepository.findAllActiveAirports();
		Assert.assertEquals(1, airportCache.getStatistics().getMemoryStoreObjectCount());
		Assert.assertEquals(1, airportCache.getStatistics().getCacheHits());
		Assert.assertEquals(1, airportCache.getStatistics().getCacheMisses());
		airportRepository.findAllActiveAirports();
		Assert.assertEquals(1, airportCache.getStatistics().getMemoryStoreObjectCount());
		Assert.assertEquals(2, airportCache.getStatistics().getCacheHits());
		Assert.assertEquals(1, airportCache.getStatistics().getCacheMisses());
	}
}
