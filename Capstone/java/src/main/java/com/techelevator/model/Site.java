package com.techelevator.model;

import java.math.BigDecimal;

public class Site {

	private long siteId;
	private long campgroundId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean accessible;
	private int maxRvLength;
	private boolean utilities;

	public void displayInfo(BigDecimal siteCost) {
		String rvString = "N/A";
		if (maxRvLength != 0) {
			rvString = String.valueOf(maxRvLength);
		}
		String accessibleString = "No";
		if (accessible) {
			accessibleString = "Yes";
		}
		String utilitiesString = "No";
		if (utilities) {
			utilitiesString = "Yes";
		}
		
		System.out.println(siteNumber + "\t\t" + maxOccupancy + "\t\t" + accessibleString + "\t\t" + rvString + "\t\t"
				+ utilitiesString + "\t\t$" + siteCost);
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

	public int getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
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

	public boolean isUtilities() {
		return utilities;
	}

	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}

}
