package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Preference;

@Service
public class PreferenceSqlDAO implements PreferenceDAO {

	private JdbcTemplate jdbcTemplate;

	public PreferenceSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void delete(Long cuisineId, Long userId) {

	}

	@Override
	public List<Preference> getAllPreferences() {
		List<Preference> preferences = new ArrayList<>();
		String sql = "SELECT * FROM preferences";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			Preference preference = mapRowToPreference(results);
			preferences.add(preference);
		}

		return preferences;

	}
	
	@Override
	public List<Preference> getCuisineChoices() {
		List<Preference> preferences = new ArrayList<>();
		String sql = "SELECT * FROM preferences";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			Preference preference = mapRowToPreference(results);
			preferences.add(preference);
		}

		return preferences;
	}

	@Override
	public void save(Long cuisineId, Long userId) {

	}

	private Preference mapRowToPreference(SqlRowSet rs) {
		Preference preference = new Preference();
		//preference.setUserId(rs.getLong("user_id"));
		preference.setCuisineId(rs.getLong("cuisine_id"));
		preference.setCuisineName(rs.getString("cuisine_name"));
		return preference;

	}

}
