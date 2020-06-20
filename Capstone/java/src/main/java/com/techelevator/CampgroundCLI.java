package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;
import com.techelevator.view.MainMenu;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private static final String MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String MENU_OPTION_SEARCH_FOR_RESERVATON = "Search for Reservation";
	private static final String MENU_OPTION_RETURN = "Return to Previous Screen";
	private static final String[] PARK_MENU_OPTIONS = { MENU_OPTION_VIEW_CAMPGROUNDS, MENU_OPTION_SEARCH_FOR_RESERVATON,
			MENU_OPTION_RETURN };
	private static final String MENU_OPTION_SEARCH_AVAILABLE_RESERVATION = "Search for Available Reservation";
	private static final String[] CAMP_MENU_OPTIONS = { MENU_OPTION_SEARCH_AVAILABLE_RESERVATION, MENU_OPTION_RETURN };

	private List<Park> parkList;
	private Park chosenPark;
	private Menu menu;
	private JDBCParkDAO pDAO;
	private JDBCSiteDAO sDAO;
	private JDBCReservationDAO rDAO;
	private JDBCCampgroundDAO cDAO;
	private Scanner userInput;

	private List<Campground> campList;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		pDAO = new JDBCParkDAO(datasource);
		sDAO = new JDBCSiteDAO(datasource);
		rDAO = new JDBCReservationDAO(datasource);
		cDAO = new JDBCCampgroundDAO(datasource);
		menu = new Menu(System.in, System.out);
		userInput = new Scanner(System.in);
	}

	public void run() {
		beginMenu();
	}

	public void beginMenu() {

		MainMenu mainMenu = new MainMenu(System.in, System.out);
		parkList = pDAO.getAllParks();

		String[] parkNames = new String[parkList.size()];

		for (int i = 0; i < parkNames.length; i++) {
			parkNames[i] = parkList.get(i).getName();
		}
		while (true) {
			String choice = (String) mainMenu.getChoiceFromOptions(parkNames);

			if (choice.equals("quit")) {
				System.exit(1);

			} else {
				for (Park p : parkList) {
					if (choice.equals(p.getName())) {
						chosenPark = p;
					}
				}
				parkMenu();
			}
		}

	}

	private void parkMenu() {
		chosenPark.displayInfo();
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(PARK_MENU_OPTIONS);
			if (choice.equals(MENU_OPTION_VIEW_CAMPGROUNDS)) {
				campMenu();
			} else if (choice.equals(MENU_OPTION_SEARCH_FOR_RESERVATON)) {
				searchParkWide();
			} else if (choice.equals(MENU_OPTION_RETURN)) {
				break;
			}

		}

	}

	private void campMenu() {
		campList = cDAO.getCampgroundsByParkId(chosenPark.getParkId());
		displayCampgrounds();
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(CAMP_MENU_OPTIONS);
			if (choice.equals(MENU_OPTION_SEARCH_AVAILABLE_RESERVATION)) {
				searchAvailableReservation();
			} else if (choice.equals(MENU_OPTION_RETURN)) {
				break;
			}
		}
	}

	private void searchAvailableReservation() {
		displayCampgrounds();
		while (true) {
			Campground campChoice = campgroundChoice();
			if (campChoice == null) {
				break;
			}
			LocalDate fromDate = fromDateChoice();
			LocalDate toDate = toDateChoice();

			if (fromDate.getMonthValue() < campChoice.getOpenMonth()) {
				System.out.println("The park has not open yet.");
			}
			if (toDate.getMonthValue() > campChoice.getCloseMonth()) {
				System.out.println("The park has closed for the year.");
			}
			if (toDate.isBefore(fromDate)) {
				System.out.println("You entered a departure before your arrival. Please try again.");
				continue;
			}

			List<Site> openSite = sDAO.getSitesFromCampId(campChoice.getCampgroundId());
			if (openSite.size() == 0) {
				System.out.println("There are no available sites.");
				continue;
			}
			System.out.println("Site Number\t\tOccupancy\t\tAccesible\t\tRVLength\t\tUtilities\t\tSite Cost");
			for (Site s : openSite) {
				s.displayInfo(campChoice.getDailyFee());
			}
			Site chosenSite = siteChoice(openSite);
			if (chosenSite == null) {
				break;
			}
			String resName = resNameChoice();
			createReservation(chosenSite.getSiteId(), resName, fromDate, toDate);
		}
	}

	private void searchParkWide() {
		while (true) {

			LocalDate fromDate = fromDateChoice();
			LocalDate toDate = toDateChoice();

//		if (fromDate.getMonthValue() < campChoice.getOpenMonth()) {
//			System.out.println("The park has not open yet.");
//		}
//		if (toDate.getMonthValue() > campChoice.getCloseMonth()) {
//			System.out.println("The park has closed for the year.");
//		}
			if (toDate.isBefore(fromDate)) {
				System.out.println("You entered a departure before your arrival. Please try again.");
				continue;
			}

			List<Site> openSite = sDAO.getSitesFromAllCampsAtPark(chosenPark.getParkId());

			if (openSite.size() == 0) {
				System.out.println("There are no available sites.");
				continue;
			}
			System.out.println("Site Number\t\tOccupancy\t\tAccesible\t\tRVLength\t\tUtilities\t\tSite Cost");
			for (Site s : openSite) {
				for (Campground c : campList) {
					if (c.getCampgroundId() == s.getCampgroundId()) {
						s.displayInfo(c.getDailyFee());
					}
				}
			}
			Site chosenSite = siteChoice(openSite);
			if (chosenSite == null) {
				break;
			}
			String resName = resNameChoice();
			createReservation(chosenSite.getSiteId(), resName, fromDate, toDate);

		}

	}

	private void displayCampgrounds() {
		System.out.println("Park Campgrounds");
		System.out.println(chosenPark.getName() + " National Park Campgrounds ");
		System.out.println();
		System.out.println("Camp No.  Name                             Open          Close          Daily Fee");
		for (Campground c : campList) {
			c.displayInfo();
		}
		System.out.println();
	}

	private Campground campgroundChoice() {
		Object campChoice = null;
		while (campChoice == null) {

			System.out.println("Which Campground? (enter 0 to cancel):  ");
			try {
				int input = Integer.valueOf(userInput.nextLine());
				if (input == 0) {
					return null;
				}
				for (Campground c : campList) {
					if (input == c.getCampgroundId()) {
						campChoice = c;
					}
				}

			} catch (NumberFormatException e) {
				// eat exception
			}
			if (campChoice == null) {
				System.out.println("Invalid Input");
			}
		}
		return (Campground) campChoice;
	}

	private LocalDate fromDateChoice() {
		Object dateChoice = null;
		Exception ex = new Exception();
		while (dateChoice == null) {

			System.out.println("What is your arrival date? (mm/dd/yyyy):  ");
			try {
				String input = userInput.nextLine();
				String[] dateInput = input.split("/");
				if (dateInput[2].length() != 4) {

					throw ex;
				}
				int[] dateArray = new int[dateInput.length];
				for (int i = 0; i < dateInput.length; i++) {
					dateArray[i] = Integer.parseInt(dateInput[i]);
				}
				LocalDate date = LocalDate.of(dateArray[2], dateArray[0], dateArray[1]);
				dateChoice = date;

			} catch (Exception e) {
				// eat exception
			}
			if (dateChoice == null) {
				System.out.println("Invalid Input");
			}
		}
		return (LocalDate) dateChoice;
	}

	private LocalDate toDateChoice() {
		Object dateChoice = null;
		Exception ex = new Exception();
		while (dateChoice == null) {

			System.out.println("What is your departure date? (mm/dd/yyyy):  ");
			try {
				String input = userInput.nextLine();
				String[] dateInput = input.split("/");
				if (dateInput[2].length() != 4) {

					throw ex;
				}
				int[] dateArray = new int[dateInput.length];
				for (int i = 0; i < dateInput.length; i++) {
					dateArray[i] = Integer.parseInt(dateInput[i]);
				}
				LocalDate date = LocalDate.of(dateArray[2], dateArray[0], dateArray[1]);
				dateChoice = date;

			} catch (Exception e) {
				// eat exception
			}
			if (dateChoice == null) {
				System.out.println("Invalid Input");
			}
		}
		return (LocalDate) dateChoice;
	}

	private Site siteChoice(List<Site> siteList) {
		Object siteChoice = null;
		while (siteChoice == null) {

			System.out.println("Which Site? (enter 0 to cancel):  ");
			try {
				int input = Integer.valueOf(userInput.nextLine());
				if (input == 0) {
					return null;
				}
				for (Site s : siteList) {
					if (input == s.getSiteNumber()) {
						siteChoice = s;
					}
				}

			} catch (NumberFormatException e) {
				// eat exception
			}
			if (siteChoice == null) {
				System.out.println("Invalid Input");
			}
		}
		return (Site) siteChoice;
	}

	private String resNameChoice() {
		Object name = null;

		while (name == null) {

			System.out.println("Enter a name to make a reservation under:  ");
			try {
				String input = userInput.nextLine();
				name = input;

			} catch (Exception e) {
				// eat exception
			}
			if (name == null) {
				System.out.println("Invalid Input");
			}
		}

		return (String) name;
	}

	private void createReservation(long siteId, String name, LocalDate fromDate, LocalDate toDate) {
		Reservation newRes = new Reservation();
		newRes.setSiteId(siteId);
		newRes.setName(name);
		newRes.setFromDate(fromDate);
		newRes.setToDate(toDate);
		newRes.setCreateDate(LocalDate.now());

		newRes = rDAO.createReservation(newRes);

		System.out.println("The reservation has been made and the confirmation id is " + newRes.getReservationId());
		beginMenu();
	}

}
