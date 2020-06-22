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
import com.techelevator.model.jdbc.JDBCCampgroundDAO;


public class JDBCCampgroundDAOIntegrationTest extends DAOIntegrationTest {

	private JdbcTemplate dao;
	
	@Before
	public void createTemplate() {
		dao = new JdbcTemplate(super.getDataSource());
	}
	
	@Test
	public void get_campgrounds_by_park_id_test() {
		JDBCCampgroundDAO campDAO = new JDBCCampgroundDAO(super.getDataSource());
		List<Campground> testCamps = new ArrayList<>();
		List<Campground> resultCamps = new ArrayList<>();
		String insertCamps = "INSERT INTO campground (park_id, name, open_from_mm, open_to_mm, daily_fee) VALUES (?,?,?,?,?) RETURNING campground_id";
		final long PARK_ID = 2;
		
		cleanDatabase();
		
		testCamps.add(getCampground(PARK_ID, "Camp1", 2, 10, BigDecimal.TEN));
		testCamps.add(getCampground(PARK_ID, "Camp2", 3, 9, BigDecimal.TEN));
		
		for (Campground camp: testCamps) {
			long campId = dao.queryForObject(insertCamps, Long.class, camp.getParkId(), camp.getName(), camp.getOpenMonth(), 
					camp.getCloseMonth(), camp.getDailyFee());
			camp.setCampgroundId(campId);
		}
		
		resultCamps = campDAO.getCampgroundsByParkId(PARK_ID);

		assertEquals(testCamps.size(), resultCamps.size());
		for (int x = 0; x < testCamps.size(); x++) {
			assertCampgroundEquals(testCamps.get(x), resultCamps.get(x));
		}
		
	}
	
	private Campground getCampground(long park_id, String name, int openMonth, int closeMonth, BigDecimal dailyFee) {
		Campground camp = new Campground();

		camp.setParkId(park_id);
		camp.setName(name);
		camp.setOpenMonth(openMonth);
		camp.setCloseMonth(closeMonth);
		camp.setDailyFee(dailyFee);
		
		return camp;
	}
	
	private void assertCampgroundEquals(Campground expected, Campground actual) {
		assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
		assertEquals(expected.getParkId(), actual.getParkId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getOpenMonth(), actual.getOpenMonth());
		assertEquals(expected.getCloseMonth(), actual.getCloseMonth());
		assertEquals(expected.getDailyFee(), actual.getDailyFee());
	}
	
	private void cleanDatabase() {
		String sqlStatement = "DELETE FROM reservation";
		dao.update(sqlStatement);
		sqlStatement = "DELETE FROM site";
		dao.update(sqlStatement);
		sqlStatement = "DELETE FROM campground";
		dao.update(sqlStatement);
//		sqlStatement = "DELETE FROM park";
//		dao.update(sqlStatement);
		

	}
}
