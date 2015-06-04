package com.nearsoft.flights.domain.services;

import com.nearsoft.flights.rest.resources.CacheResource.CacheType;

public interface CacheService {
	void clean(CacheType type);
	
}
