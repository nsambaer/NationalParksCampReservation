package com.techelevator.model.jdbc;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate executeSQL;

	public JDBCSiteDAO(DataSource dataSource) {
		this.executeSQL = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getSitesFromCampId(long campId) {
		List<Site> siteList = new ArrayList<>();

		String sqlgetSiteCampId = "SELECT * FROM site s WHERE campground_id = ?";
		SqlRowSet results = executeSQL.queryForRowSet(sqlgetSiteCampId, campId);

		while (results.next()) {
			Site site = mapRowToSite(results);
			siteList.add(site);

		}

		return siteList;

	}
	
	@Override
	public List<Site> getSitesFromAllCampsAtPark(long parkId) {
		List<Site> siteList = new ArrayList<>();
		
		String sqlgetSiteCampId = "SELECT * FROM site s INNER JOIN campground c ON s.campground_id = c.campground_id WHERE c.park_id = ?";
		SqlRowSet results = executeSQL.queryForRowSet(sqlgetSiteCampId, parkId);

		while (results.next()) {
			Site site = mapRowToSite(results);
			siteList.add(site);
		}
		
		
		return siteList;
	}
	

	private Site mapRowToSite(SqlRowSet results) {
		Site site = new Site();

		site.setSiteId(results.getLong("site_id"));
		site.setCampgroundId(results.getLong("campground_id"));
		site.setSiteNumber(results.getInt("site_number"));
		site.setMaxOccupancy(results.getInt("max_occupancy"));
		site.setAccessible(results.getBoolean("accessible"));
		site.setMaxRvLength(results.getInt("max_rv_length"));
		site.setUtilities(results.getBoolean("utilities"));
		return site;
	}

	
	//Deprecated to avoid logic errors and move more validation to the CLI
//	@Override
//	public List<Site> getOpenSites(long campId, LocalDate fromDate, LocalDate toDate) {
//		List<Site> siteList = new ArrayList<>();
//		List<Integer> blackList = new ArrayList<>();
//
//		String sqlgetSiteCampId = "SELECT * FROM site s LEFT JOIN reservation r ON s.site_id = r.site_id "
//				+ "WHERE campground_id = ?";
//		SqlRowSet results = executeSQL.queryForRowSet(sqlgetSiteCampId, campId);
//		int listCount = 0;
//		while (results.next() && listCount < 5) {
//			int siteId = results.getInt("site_id");
//			try {
//				LocalDate resFromDate = (results.getDate("from_date").toLocalDate()); // this and the line below will
//																						// throw null exception if date
//																						// is null
//				LocalDate resToDate = (results.getDate("to_date").toLocalDate());
//				if (fromDate.isAfter(resFromDate) && fromDate.isBefore(resToDate)) {
//					blackList.add(siteId);
//				} else if (toDate.isAfter(resFromDate) && toDate.isBefore(resToDate)) {
//					blackList.add(siteId);
//				}
//			} catch (NullPointerException e) {
//				// eating null exception because that means that there are no reservations for
//				// that site
//			}
//			if (blackList.contains(siteId)) {
//
//			} else {
//				Site site = mapRowToSite(results);
//				siteList.add(site);
//				blackList.add(siteId);
//				listCount++;
//			}
//		}
//
//		return siteList;
//	}

//	public List<Site> getOpenSitesPW(long parkId, LocalDate fromDate, LocalDate toDate) {
//
//		List<Site> siteList = new ArrayList<>();
//		List<Integer> blackList = new ArrayList<>();
//
//		String sqlgetSiteCampId = "SELECT * FROM site s LEFT JOIN reservation r ON s.site_id = r.site_id "
//				+ "INNER JOIN campground c ON s.campground_id = c.campground_id WHERE c.park_id = ?";
//		SqlRowSet results = executeSQL.queryForRowSet(sqlgetSiteCampId);
//		int listCount = 0;
//		while (results.next() && listCount < 5) {
//			int siteId = results.getInt("site_id");
//			try {
//				LocalDate resFromDate = (results.getDate("from_date").toLocalDate()); // this and the line below will
//																						// throw null exception if date
//																						// is null
//				LocalDate resToDate = (results.getDate("to_date").toLocalDate());
//				if (fromDate.isAfter(resFromDate) && fromDate.isBefore(resToDate)) {
//					blackList.add(siteId);
//				} else if (toDate.isAfter(resFromDate) && toDate.isBefore(resToDate)) {
//					blackList.add(siteId);
//				}
//			} catch (NullPointerException e) {
//				// eating null exception because that means that there are no reservations for
//				// that site
//			}
//			if (blackList.contains(siteId)) {
//
//			} else {
//				Site site = mapRowToSite(results);
//				siteList.add(site);
//				blackList.add(siteId);
//				listCount++;
//
//			}
//		}
//		return siteList;
//	}

}
