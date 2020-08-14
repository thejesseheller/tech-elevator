package com.techelevator.model;

import java.util.Objects;

public class Favorite {

    private Long restaurantId;
    private String restaurantName;
    private String cuisine;
    private String phoneNumber;
    private String rating;
    private String description;
    private String featuredImage;
    private String currency;
    private String menuUrl;
    private String url;

	public Favorite() {	}

    public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFeaturedImage() {
		return featuredImage;
	}

	public void setFeaturedImage(String featuredImage) {
		this.featuredImage = featuredImage;
	}

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Favorite favorite = (Favorite) o;
		return Objects.equals(getRestaurantId(), favorite.getRestaurantId()) &&
				Objects.equals(getRestaurantName(), favorite.getRestaurantName()) &&
				Objects.equals(getCuisine(), favorite.getCuisine()) &&
				Objects.equals(getPhoneNumber(), favorite.getPhoneNumber()) &&
				Objects.equals(getRating(), favorite.getRating()) &&
				Objects.equals(getDescription(), favorite.getDescription()) &&
				Objects.equals(getFeaturedImage(), favorite.getFeaturedImage()) &&
				Objects.equals(getCurrency(), favorite.getCurrency()) &&
				Objects.equals(getMenuUrl(), favorite.getMenuUrl()) &&
				Objects.equals(getUrl(), favorite.getUrl());
	}
}
