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

	
	public void displayInfo() {
		System.out.println();
		System.out.println("Park Information Screen");
		System.out.println(name + " National Park");
		System.out.println("Location:\t\t" + location);
		System.out.println("Established:\t\t" + establishDate);
		System.out.println("Area:\t\t\t" + area + " sq km");
		System.out.println("Annual Visitors:\t" + visitors);
		System.out.println();
		formatDescription();

	}
	//prints the description with some newline characters
	private void formatDescription() {
		int length = description.length();
		int end = 100; //var to delineate the end of the substring
		for (int begin = 0; begin < length; begin += 100) { //want to print 100 characters or so every line
			int offset = 0; //offset is to keep track of how many characters over 100 we went, need to add to begin *after* substring is printed
			try {
				while (!description.substring(end, end + 1).equals(" ")) { //making sure newline doesn't come in the middle of a word
					end++;
					offset++;
				}
				end++;
				offset++; //these two increment once to put the newline after the space
				System.out.println(description.substring(begin,end));
				begin += offset;
				}
			catch (StringIndexOutOfBoundsException e) { //when the last part of the string is less than 100 characters out of bound exception is thrown, and this prints whatever is left
				System.out.println(description.substring(begin));
			}
			finally {end += 100;} //wont auto increment in for loop
		}
			
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
