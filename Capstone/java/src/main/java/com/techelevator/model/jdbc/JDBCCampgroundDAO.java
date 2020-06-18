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
	
	
	public List<Campground> getCampgroundsByParkId(long parkId) {
		String sqlGetCampParkId = "SELECT * FROM campground WHERE park_id = ?";
		
		SqlRowSet results = executeSQL.queryForRowSet(sqlGetCampParkId, parkId);
		
		List<Campground> campList = new ArrayList<>();
		
		
		return new ArrayList<Campground>();
	}
	
	
}
