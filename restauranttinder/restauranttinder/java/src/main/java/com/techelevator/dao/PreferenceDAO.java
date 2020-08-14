package com.techelevator.dao;

import java.util.List;

import com.techelevator.model.Preference;

public interface PreferenceDAO {

	void delete(Long cuisineId, Long userId);

    List<Preference> getAllPreferences();
    
    void save(Long cuisineId, Long userId);

	List<Preference> getCuisineChoices();
	
}
