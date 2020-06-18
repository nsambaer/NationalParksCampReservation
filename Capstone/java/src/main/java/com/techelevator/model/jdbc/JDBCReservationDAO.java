package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate executeSQL;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.executeSQL = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Reservation> getReservationsBySiteId(long siteId) {
		List<Reservation> reservList = new ArrayList<>();
		
		String sqlGetReservSiteId = "SELECT * FROM reservation WHERE site_id = ?";
		SqlRowSet results = executeSQL.queryForRowSet(sqlGetReservSiteId, siteId);
		
		while (results.next()) {
			Reservation reserv = mapRowToReservation(results);
			reservList.add(reserv);
		}
		
		return null;
	}

	@Override
	public Reservation createReservation(Reservation newRes) {
		String sqlInsertNewReservation = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) VALUES (?,?,?,?, ?)";
		Long resId = executeSQL.queryForObject(sqlInsertNewReservation, Long.class, newRes.getName(), newRes.getFromDate(), newRes.getToDate(),
				newRes.getCreateDate());
		newRes.setReservationId(resId);
		
		
		return newRes;
	}
	
	
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation reserv = new Reservation();
		
		reserv.setReservationId(results.getLong("reservation_id"));
		reserv.setSiteId(results.getLong("site_id"));
		reserv.setName(results.getString("name"));
		reserv.setFromDate(results.getDate("from_date").toLocalDate());
		reserv.setToDate(results.getDate("to_date").toLocalDate());
		reserv.setCreateDate(results.getDate("create_date").toLocalDate());
		
		return reserv;		
	}
	

}
