package com.nearsoft.flights.domain.model.airport;

import java.util.Set;
import java.util.stream.Collectors;

import com.nearsoft.flights.domain.model.airport.Airport.AirportBuilder;
import com.nearsoft.flights.domain.model.exception.RepositoryException;
import com.nearsoft.flights.interfaces.AirportApi;
import com.nearsoft.flights.persistence.dao.AirportDao;
import com.nearsoft.flights.persistence.dao.jdbc.PersistenceException;
import com.nearsoft.flights.persistence.dto.AirportDto;

public class AirportRepositoryImpl implements AirportRepository {
	
	private AirportApi airportApi;
	
	private AirportDao airportDao;
	
	public AirportRepositoryImpl(AirportApi airportApi, AirportDao airportDao) {
		this.airportApi = airportApi;
		this.airportDao = airportDao;
	}

	@Override
	public Set<Airport> findAllActiveAirports() throws RepositoryException {
		try {
			Set<AirportDto> airportsDto = airportDao.findAll();
			if(airportsDto == null || airportsDto.isEmpty()) {
				Set<Airport> airports = airportApi.getAllActiveAirports();
				airportDao.insert(getAirportsDtoFromAirports(airports));
				return airports;
			} else {
				return getAirportsFromAirportsDto(airportsDto);
			}
		} catch(PersistenceException e) {
			throw new RepositoryException("Error ocurred while perfoming operations in database for finding all airports", e);
		}
	}
	
	@Override
	public Airport findByAirportCode(String airportCode) throws RepositoryException {
		try {
			AirportDto airportDto = airportDao.findByAirportCode(airportCode);
			if(airportDto == null || "".equals(airportDto.getAirportCode())) {
				Airport airport = airportApi.getAirportByCode(airportCode);
				airportDao.insert(airportToAirportDto(airport));
				return airport;
			} else {
				return airportDtoToAirport(airportDto);
			}
		} catch(PersistenceException e) {
			throw new RepositoryException("Error ocurred while perfoming operations in database for finding airport by code ["+airportCode+"]", e);
		}
	}
	
	private Set<Airport> getAirportsFromAirportsDto(Set<AirportDto> dtos) {
		return dtos.stream().map(airportDto -> airportDtoToAirport(airportDto)).collect(Collectors.toSet());
	}
	
	private Set<AirportDto> getAirportsDtoFromAirports(Set<Airport> airports) {
		return airports.stream().map(airport -> airportToAirportDto(airport)).collect(Collectors.toSet());
	}
	
	private AirportDto airportToAirportDto(Airport airport) {
		AirportDto dto = new AirportDto();
		dto.setAirportCode(airport.getAirportCode());
		dto.setAirportName(airport.getName());
		dto.setCity(airport.getCity());
		dto.setCityCode(airport.getCityCode());
		dto.setCountryCode(airport.getCountryCode());
		dto.setCountryName(airport.getCountryName());
		dto.setLatitude(airport.getLatitude());
		dto.setLongitude(airport.getLongitude());
		return dto;
	}
	
	private Airport airportDtoToAirport(AirportDto airport){
		AirportBuilder builder = new AirportBuilder(airport.getAirportCode());
		builder.addCity(airport.getCity())
			.addCityCode(airport.getCityCode())
			.addCountryCode(airport.getCountryCode())
			.addCountryName(airport.getCountryName())
			.addLatitude(airport.getLatitude())
			.addLongitude(airport.getLongitude())
			.addName(airport.getAirportName());
		return builder.build();
	}
	
}
