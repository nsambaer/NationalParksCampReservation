package com.techelevator.model;

import java.math.BigDecimal;

public class Campground {
	
	private long campgroundId; 
	private long parkId; 
	private String name; 
	private int openMonth; 
	private int closeMonth; 
	private BigDecimal dailyFee;
	
	
	public String monthName(int month) {
		String name = ""; 
		switch(month) {
		case 1:
			name = "January";
			break; 
		case 2:
			name = "February";
			break;
		case 3:
			name = "March"; 
			break;
		case 4: 
			name = "April"; 
			break;
		case 5: 
			name = "May"; 
			break;
		case 6:
			name = "June"; 
			break;
		case 7:
			name = "July"; 
			break;
		case 8: 
			name = "August"; 
			break;
		case 9: 
			name = "September";
			break;
		case 10:
			name = "October"; 
			break;
		case 11:
			name = "November"; 
			break;
		case 12:
			name = "December"; 
			break;
		}

		return name; 
	}
		
	//           The Unnamed Primitive Campsites
//	Camp No.  Name                             Open          Close          Daily Fee
	// need to clean up standardize spacing -- need some logic to print out nice and neat
	public void displayInfo() {
		String formatId = String.valueOf(campgroundId);
		int idLength = formatId.length();
		for (int i = 0; i <= (10 - idLength); i++) {
			formatId += " ";
		}
		
		String formatName = name;
		int nameLength = formatName.length();
		for (int i = 0; i <= (33 - nameLength); i++) {
			formatName += " ";
		}
		
		String formatOpen = monthName(openMonth);
		int openLength = formatOpen.length();
		for (int i = 0; i <= (14 - openLength); i++) {
			formatOpen += " ";
		}
		
		String formatClose = monthName(closeMonth);
		int closeLength = formatClose.length();
		for (int i = 0; i <= (15 - closeLength); i++) {
			formatClose += " ";
		}
		
		System.out.println("#" + formatId + formatName + formatOpen + formatClose + "$" + dailyFee);
		
	}
	
	
	public long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public long getParkId() {
		return parkId;
	}
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOpenMonth() {
		return openMonth;
	}
	public void setOpenMonth(int openMonth) {
		this.openMonth = openMonth;
	}
	public int getCloseMonth() {
		return closeMonth;
	}
	public void setCloseMonth(int closeMonth) {
		this.closeMonth = closeMonth;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
		this.dailyFee = this.dailyFee.setScale(2);
	}
	
	
	
}
