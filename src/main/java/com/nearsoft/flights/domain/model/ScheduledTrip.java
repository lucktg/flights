package com.nearsoft.flights.domain.model;

import java.util.Date;

public class ScheduledTrip {
	private Airport airport;
	private Date scheduledDate;
	private String terminal;
	
	public ScheduledTrip(Airport aiport, Date scheduledDate, String terminal) {
		this.airport = aiport;
		this.scheduledDate = scheduledDate;
		this.terminal = terminal;
	}
	
	public Airport getAirport() {
		return airport;
	}
	
	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getTerminal() {
		return terminal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airport == null) ? 0 : airport.hashCode());
		result = prime * result
				+ ((scheduledDate == null) ? 0 : scheduledDate.hashCode());
		result = prime * result
				+ ((terminal == null) ? 0 : terminal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduledTrip other = (ScheduledTrip) obj;
		if (airport == null) {
			if (other.airport != null)
				return false;
		} else if (!airport.equals(other.airport))
			return false;
		if (scheduledDate == null) {
			if (other.scheduledDate != null)
				return false;
		} else if (!scheduledDate.equals(other.scheduledDate))
			return false;
		if (terminal == null) {
			if (other.terminal != null)
				return false;
		} else if (!terminal.equals(other.terminal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder("ScheduledTrip [airport=")
			.append(airport)
			.append(", scheduledDate=")
			.append(scheduledDate)
			.append(", terminal=")
			.append(terminal)
			.append("]").toString();
	}
	
	
}
