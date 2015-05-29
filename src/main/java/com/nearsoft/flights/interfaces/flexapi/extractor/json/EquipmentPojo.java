package com.nearsoft.flights.interfaces.flexapi.extractor.json;

public class EquipmentPojo {
	private String iata;
	private String name;
	private String turboProp;
	private String jet;
	private String widebody;
	private String regional;
	
	public EquipmentPojo() {}

	protected String getIata() {
		return iata;
	}

	protected String getName() {
		return name;
	}

	protected String getTurboProp() {
		return turboProp;
	}

	protected String getJet() {
		return jet;
	}

	protected String getWidebody() {
		return widebody;
	}

	protected String getRegional() {
		return regional;
	}
}
