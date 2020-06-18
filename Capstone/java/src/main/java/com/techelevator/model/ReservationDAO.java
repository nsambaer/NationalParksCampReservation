package com.techelevator.model;

import java.util.List;

public interface ReservationDAO {

	public List<Reservation> getReservationsBySiteId(long siteId);
	
	public Reservation createReservation(long siteId);
	
	
	
}
