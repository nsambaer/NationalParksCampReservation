package com.techelevator.model;

import java.time.LocalDate;

public class Park {
	
	private long parkId; 
	private String name; 
	private String location; 
	private LocalDate establishDate; 
	private int area; 
	private int visitors; 
	private String description;
	
	// clean up later 
	public void displayInfo() {
		System.out.println("Park Information Screen");
		System.out.println("Location: \t" + location );
		System.out.println("Established: \t" +  establishDate);
		System.out.println("Area: \t" + area + " Sq  km");
		System.out.println("Annual Visitors: \t" + visitors);
		System.out.println(); 
		System.out.println(description);
		
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstablishDate() {
		return establishDate;
	}
	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getVisitors() {
		return visitors;
	}
	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	} 
	
	
	
	
	
	
	

}
