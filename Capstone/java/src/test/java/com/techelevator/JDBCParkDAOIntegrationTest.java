package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.jdbc.JDBCParkDAO;

public class JDBCParkDAOIntegrationTest extends DAOIntegrationTest {

	private JdbcTemplate dao;

	@Before
	public void createTemplate() {
		dao = new JdbcTemplate(super.getDataSource());
	}

	@Test
	public void get_all_parks_test() {
		JDBCParkDAO parkDAO = new JDBCParkDAO(super.getDataSource());
		List<Park> testParks = new ArrayList<>();
		List<Park> resultParks = new ArrayList<>();
		String insertParks = "INSERT INTO park (name, location, establish_date, area, visitors, description) VALUES (?,?,?,?,?, ?) RETURNING park_id";

		cleanDatabase();

		testParks.add(getPark("Park1", "Idaho", LocalDate.now(), 20, 4, "A test park"));
		testParks.add(getPark("Park2", "Montana", LocalDate.now(), 14, 11, "Another test park"));

		for (Park park : testParks) {
			long parkId = dao.queryForObject(insertParks, Long.class, park.getName(), park.getLocation(),
					park.getEstablishDate(), park.getArea(), park.getVisitors(), park.getDescription());
			park.setParkId(parkId);
		}

		resultParks = parkDAO.getAllParks();

		assertEquals(testParks.size(), resultParks.size());
		for (int x = 0; x < testParks.size(); x++) {
			assertParkEquals(testParks.get(x), resultParks.get(x));
		}

	}

	private Park getPark(String name, String location, LocalDate establishDate, int area, int visitors,
			String description) {
		Park park = new Park();

		park.setName(name);
		park.setLocation(location);
		park.setEstablishDate(establishDate);
		park.setArea(area);
		park.setVisitors(visitors);
		park.setDescription(description);

		return park;
	}

	private void assertParkEquals(Park expected, Park actual) {
		assertEquals(expected.getParkId(), actual.getParkId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getEstablishDate(), actual.getEstablishDate());
		assertEquals(expected.getArea(), actual.getArea());
		assertEquals(expected.getVisitors(), actual.getVisitors());
		assertEquals(expected.getDescription(), actual.getDescription());
	}

	private void cleanDatabase() {
		String sqlStatement = "DELETE FROM reservation";
		dao.update(sqlStatement);
		sqlStatement = "DELETE FROM site";
		dao.update(sqlStatement);
		sqlStatement = "DELETE FROM campground";
		dao.update(sqlStatement);
		sqlStatement = "DELETE FROM park";
		dao.update(sqlStatement);

	}

}
