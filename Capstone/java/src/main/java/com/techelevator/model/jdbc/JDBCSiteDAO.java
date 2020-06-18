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
	public List<Site> getSitesByCampId(long campId) {
		List<Site> siteList = new ArrayList<>(); 
		
		String sqlgetSiteCampId = "SELECT * FROM site WHERE camp_id = ?";
		SqlRowSet results = executeSQL.queryForRowSet(sqlgetSiteCampId, campId); 
		
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
}
