package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Reservation;
import com.techelevator.model.Reservation;
import com.techelevator.model.jdbc.JDBCReservationDAO;

public class JDBCReservationDAOIntegrationTest extends DAOIntegrationTest {

	private JdbcTemplate dao;
	private final long SITE_ID = 42;

	@Before
	public void createTemplate() {
		dao = new JdbcTemplate(super.getDataSource());
	}

	@Test
	public void get_reservations_by_site_id_test() {
		JDBCReservationDAO reservationDAO = new JDBCReservationDAO(super.getDataSource());
		List<Reservation> testReservations = new ArrayList<>();
		List<Reservation> resultReservations = new ArrayList<>();
		String insertReservations = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) VALUES (?,?,?,?,?) RETURNING reservation_id";

		cleanDatabase();

		testReservations.add(getReservation(SITE_ID, "Reservation1", LocalDate.of(2020, 01, 29), LocalDate.of(2020, 01, 31), LocalDate.of(2020, 01, 22)));
		testReservations.add(getReservation(SITE_ID, "Reservation2", LocalDate.of(2020, 03, 23), LocalDate.of(2020, 03, 29), LocalDate.of(2020, 03, 15)));

		for (Reservation reservation : testReservations) {
			long reservationId = dao.queryForObject(insertReservations, Long.class, reservation.getSiteId(), reservation.getName(), reservation.getFromDate(),
					reservation.getToDate(), reservation.getCreateDate());
			reservation.setReservationId(reservationId);
		}

		resultReservations = reservationDAO.getReservationsBySiteId(SITE_ID);

		assertEquals(testReservations.size(), resultReservations.size());
		for (int x = 0; x < testReservations.size(); x++) {
			assertReservationEquals(testReservations.get(x), resultReservations.get(x));
		}

	}
	
	@Test
	public void create_reservation_test() {
		JDBCReservationDAO reservationDAO = new JDBCReservationDAO(super.getDataSource());
		Reservation newRes = getReservation(SITE_ID, "Test Reservation", LocalDate.of(2020, 8, 16), LocalDate.of(2020, 8, 18), LocalDate.of(2020, 06, 21));
		
		cleanDatabase();
		
		reservationDAO.createReservation(newRes);
		
		List<Reservation> resultRes = reservationDAO.getReservationsBySiteId(SITE_ID);
		
		assertEquals(1, resultRes.size());
		assertReservationEquals(newRes, resultRes.get(0));
		
	}
	
	

	private Reservation getReservation(long siteId, String name, LocalDate fromDate, LocalDate toDate, LocalDate createDate) {
		Reservation reservation = new Reservation();

		reservation.setSiteId(siteId);
		reservation.setName(name);
		reservation.setFromDate(fromDate);;
		reservation.setToDate(toDate);;
		reservation.setCreateDate(createDate);;


		return reservation;
	}

	private void assertReservationEquals(Reservation expected, Reservation actual) {
		assertEquals(expected.getReservationId(), actual.getReservationId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getFromDate(), actual.getFromDate());
		assertEquals(expected.getToDate(), actual.getToDate());
		assertEquals(expected.getCreateDate(), actual.getCreateDate());
	}

	private void cleanDatabase() {
		String sqlStatement = "DELETE FROM reservation";
		dao.update(sqlStatement);
//		sqlStatement = "DELETE FROM site";
//		dao.update(sqlStatement);
//		sqlStatement = "DELETE FROM campground";
//		dao.update(sqlStatement);
//		sqlStatement = "DELETE FROM park";
//		dao.update(sqlStatement);

	}

}
