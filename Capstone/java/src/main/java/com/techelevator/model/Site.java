package com.techelevator.model;

public class Site {

	private long siteId; 
	private long campgroundId; 
	private int siteMumber; 
	private int maxOccupancy; 
	private boolean accessible; 
	private int maxRvLength; 
	private boolean untilities;
	
	
	
	
	public void displayInfo( int siteCost) {
		
		System.out.println( siteId + "\t \t \t" + maxOccupancy + "\t \t \t" + accessible + "\t \t \t" + maxRvLength
				+ "\t \t \t" + untilities + "\t \t \t" + siteCost);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getSiteMumber() {
		return siteMumber;
	}
	public void setSiteMumber(int siteMumber) {
		this.siteMumber = siteMumber;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
		return accessible;
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public int getMaxRvLength() {
		return maxRvLength;
	}
	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	public boolean isUntilities() {
		return untilities;
	}
	public void setUntilities(boolean untilities) {
		this.untilities = untilities;
	} 
	
	

}
