package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate executeSQL;
	
	public JDBCParkDAO(DataSource dataSource) {
		this.executeSQL = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Park> getAllParks() {
		List<Park> parkList = new ArrayList<>();
		
		String sqlGetAllParks = "SELECT * FROM park";
		SqlRowSet results = executeSQL.queryForRowSet(sqlGetAllParks);
		
		while (results.next()) {
			Park park = mapRowToPark(results);
			parkList.add(park);
		}
		
		return parkList;
	}

	@Override
	public List<Park> getParkById(long parkId) {
		//may or may not be needed
		return null;
	}

	
	private Park mapRowToPark(SqlRowSet results) {
		Park park = new Park();
		
		park.setParkId(results.getLong("park_id"));
		park.setName(results.getString("name"));
		park.setLocation(results.getString("location"));
		park.setEstablishDate(results.getDate("establish_date").toLocalDate());
		park.setArea(results.getInt("area"));
		park.setVisitors(results.getInt("visitors"));
		park.setDescription(results.getString("description"));
		return park;		
	}
	
	
	
}
