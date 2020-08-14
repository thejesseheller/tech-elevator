package com.techelevator.park;

import java.util.List;

public interface ParkDAO {

	List<Park> getAllParks();
	Park getParkByName(String parkName);

}
