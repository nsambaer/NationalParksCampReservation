package com.techelevator.model;


import java.util.List;

public interface SiteDAO {

	public List<Site> getSitesFromCampId(long campId);

	public List<Site> getSitesFromAllCampsAtPark(long parkId);
}
