package com.techelevator.campground;

import java.util.List;


public interface CampgroundDAO {
	
	List<Campground> getAllCampgrounds();
	
	Campground getCampgroundByCampgroundId(Long campgroundId);
	
	List<Campground>getCampgroundByParkId(Long parkId);

}
