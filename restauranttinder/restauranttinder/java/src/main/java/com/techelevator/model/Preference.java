package com.techelevator.model;

public class Preference {

	private Long cuisineId;
	private String cuisineName;
	private Long userId;

	public Preference(Long cuisineId) {
		this.cuisineId = cuisineId;
	}

	public Preference() {
	}

	public Long getCuisineId() {
		return cuisineId;
	}

	public void setCuisineId(Long cuisineId) {
		this.cuisineId = cuisineId;
	}

	public String getCuisineName() {
		return cuisineName;
	}

	public void setCuisineName(String cuisineName) {
		this.cuisineName = cuisineName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}