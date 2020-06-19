package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {

	public List<Site> getOpenSites(long campId, LocalDate fromDate , LocalDate toDate);
		
	
}
