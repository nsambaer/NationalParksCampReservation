package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.model.Site;
import com.techelevator.model.jdbc.JDBCSiteDAO;

public class JDBCSiteDAOIntegrationTest extends DAOIntegrationTest {

	private JdbcTemplate dao;
	private final long CAMP_ID = 1;
	private final long PARK_ID = 1;
	
	@Before
	public void createTemplate() {
		dao = new JdbcTemplate(super.getDataSource());
	}
	

	@Test
	public void get_sites_from_camp_id_test() {
		JDBCSiteDAO siteDAO = new JDBCSiteDAO(super.getDataSource());
		List<Site> testSites = new ArrayList<>();
		List<Site> resultSites = new ArrayList<>();
		String insertSites = "INSERT INTO site (campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) VALUES (?,?,?,?,?,?) RETURNING site_id";

		cleanDatabase();
		
		testSites.add(getSite(CAMP_ID, 12, 4, false, 0, true));
		testSites.add(getSite(CAMP_ID, 9, 1000000, true, 16, false));

		for (Site site : testSites) {
			long siteId = dao.queryForObject(insertSites, Long.class, site.getCampgroundId(), site.getSiteNumber(), site.getMaxOccupancy(),
					site.isAccessible(), site.getMaxRvLength(), site.isUtilities());
			site.setSiteId(siteId);
		}

		resultSites = siteDAO.getSitesFromCampId(CAMP_ID);

		assertEquals(testSites.size(), resultSites.size());
		for (int x = 0; x < testSites.size(); x++) {
			assertSiteEquals(testSites.get(x), resultSites.get(x));
		}

	}
	
	@Test
	public void get_sites_from_all_camps_at_park() {
		JDBCSiteDAO siteDAO = new JDBCSiteDAO(super.getDataSource());
		
		List<Site> testSites = new ArrayList<>();
		List<Site> resultSites = new ArrayList<>();
		String insertSites = "INSERT INTO site (campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) VALUES (?,?,?,?,?,?) RETURNING site_id";

		cleanDatabase();
		
		testSites.add(getSite(1, 12, 4, false, 0, true));
		testSites.add(getSite(2, 9, 1000000, true, 16, false));

		for (Site site : testSites) {
			long siteId = dao.queryForObject(insertSites, Long.class, site.getCampgroundId(), site.getSiteNumber(), site.getMaxOccupancy(),
					site.isAccessible(), site.getMaxRvLength(), site.isUtilities());
			site.setSiteId(siteId);
		}

		resultSites = siteDAO.getSitesFromAllCampsAtPark(PARK_ID);

		assertEquals(testSites.size(), resultSites.size());
		for (int x = 0; x < testSites.size(); x++) {
			assertSiteEquals(testSites.get(x), resultSites.get(x));
		}

		
	}
	
	

	private Site getSite(long campgroundId, int siteNumber, int maxOccupancy, boolean accessible, int maxRvLength, boolean utilities) {
		Site site = new Site();

		site.setCampgroundId(campgroundId);
		site.setSiteNumber(siteNumber);
		site.setMaxOccupancy(maxOccupancy);
		site.setAccessible(accessible);
		site.setMaxRvLength(maxRvLength);
		site.setUtilities(utilities);


		return site;
	}
	

	private void assertSiteEquals(Site expected, Site actual) {
		assertEquals(expected.getSiteId(), actual.getSiteId());
		assertEquals(expected.getSiteNumber(), actual.getSiteNumber());
		assertEquals(expected.getMaxOccupancy(), actual.getMaxOccupancy());
		assertEquals(expected.isAccessible(), actual.isAccessible());
		assertEquals(expected.getMaxRvLength(), actual.getMaxRvLength());
		assertEquals(expected.isUtilities(), actual.isUtilities());
	}
	

	private void cleanDatabase() {
		String sqlStatement = "DELETE FROM reservation";
		dao.update(sqlStatement);
		sqlStatement = "DELETE FROM site";
		dao.update(sqlStatement);
//		sqlStatement = "DELETE FROM campground";
//		dao.update(sqlStatement);
//		sqlStatement = "DELETE FROM site";
//		dao.update(sqlStatement);

	}
	
}
