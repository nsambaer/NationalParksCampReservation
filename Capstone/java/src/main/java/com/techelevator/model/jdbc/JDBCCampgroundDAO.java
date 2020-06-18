package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO{

	private JdbcTemplate executeSQL;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
		this.executeSQL = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> getCampgroundsByParkId(long parkId) {
		String sqlGetCampParkId = "SELECT * FROM campground WHERE park_id = ?";
		
		SqlRowSet results = executeSQL.queryForRowSet(sqlGetCampParkId, parkId);
		
		List<Campground> campList = new ArrayList<>();
		while (results.next()) {
			Campground camp = mapRowToCampground(results);
			campList.add(camp);			
		}
		
		return campList;
	}
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground camp = new Campground();
		
		camp.setCampgroundId(results.getLong("campground_id"));
		camp.setParkId(results.getLong("park_id"));
		camp.setName(results.getString("name"));
		camp.setOpenMonth(results.getInt("open_from_mm"));
		camp.setCloseMonth(results.getInt("open_to_mm"));
		camp.setDailyFee(results.getInt("daily_fee"));
		return camp;		
	}
	
	
}
