package com.techelevator;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
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

	private List<Park> parkList;
	private Park chosenPark;
	private Menu menu;
	private JDBCParkDAO pDAO;  
	private JDBCSiteDAO sDAO; 
	private JDBCReservationDAO rDAO;  
	private JDBCCampgroundDAO cDAO; 
	
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
	}

	public void run() {

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
				// searchParkWide();
			} else if (choice.equals(MENU_OPTION_RETURN)) {
				break;
			}

		}

	}

	private void campMenu() {
		campList = cDAO.getCampgroundsByParkId(chosenPark.getParkId()); 
		displayCampgrounds(); 
	}
	private void displayCampgrounds() {
		System.out.println("Park Campgrounds");
		System.out.println( chosenPark.getName() + " National Park Campgrounds " );
		System.out.println( );
		System.out.println( "\tName \t \t \tOpen \t\tClose \t\tDaily Fee");
	 for (Campground c: campList) {
		 c.displayInfo();
	 }
	}
	
	
}
