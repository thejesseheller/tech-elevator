package com.techelevator.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.model.Favorite;

@Service
public class FavoriteSqlDAO implements FavoriteDAO {

	private JdbcTemplate jdbcTemplate;

	public FavoriteSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean existsInFavorites(Favorite favorite) {

	    String sql = "SELECT COUNT(*) FROM favorites WHERE favorite_api_id = ?";
	    int count = jdbcTemplate.queryForObject(sql, new Object[] {favorite.getRestaurantId()}, Integer.class);
	    return count > 0;
    }

    @Override
	public boolean existsInUserFavorite(Long userId, Favorite favorite) {

		String sql = "SELECT COUNT(*) FROM user_favorites WHERE user_id = ? AND favorite_api_id = ?";
		int count = jdbcTemplate.queryForObject(sql, new Object[] {userId, favorite.getRestaurantId()}, Integer.class);
		return count > 0;

	}

	@Override
	public void addToFavorites(Long userId, Favorite favorite) {

	    if (!existsInFavorites(favorite)) {
			String insertFavorite = "INSERT INTO favorites(favorite_api_id, favorite_name, cuisine, phone_number, ratings, description, featured_image, currency, url, menu_url) "
								  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(insertFavorite, favorite.getRestaurantId(), favorite.getRestaurantName(),
				favorite.getCuisine(), favorite.getPhoneNumber(), favorite.getRating(), favorite.getDescription(),
				favorite.getFeaturedImage(), favorite.getCurrency(), favorite.getUrl(), favorite.getMenuUrl());
        }

	    if (!existsInUserFavorite(userId, favorite)) {
			String insertUserIdAndFavorite = "INSERT INTO user_favorites (user_id, favorite_api_id) VALUES (?, ?)";
			jdbcTemplate.update(insertUserIdAndFavorite, userId, favorite.getRestaurantId());
		}

	}

	@Override
	public void removeFromFavorites(Long userId, Long resId) {
//		String deleteFavorite = "DELETE FROM favorites(favorite_api_id, favorite_name, cuisine, phone_number, ratings, description, featured_image, currency, url, menu_url) "
//				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//		jdbcTemplate.update(deleteFavorite, favorite.getRestaurantId(), favorite.getRestaurantName(),
//				favorite.getCuisine(), favorite.getPhoneNumber(), favorite.getRating(), favorite.getDescription(),
//				favorite.getFeaturedImage(), favorite.getCurrency(), favorite.getUrl(), favorite.getMenuUrl());

		String deleteUserIdAndFavorite = "DELETE FROM user_favorites WHERE user_id = ? AND favorite_api_id = ?";
		jdbcTemplate.update(deleteUserIdAndFavorite, userId, resId);
	}

	@Override
	public List<Favorite> getAllFavorites(Long userId) {
		List<Favorite> favorites = new ArrayList<>();
		String sql = "SELECT *" + " FROM favorites INNER JOIN user_favorites ON favorites.favorite_api_id"
				+ " = user_favorites.favorite_api_id WHERE user_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		while (results.next()) {
			Favorite favorite = mapRowToFavorite(results);
			favorites.add(favorite);
		}

		return favorites;
	}

	private Favorite mapRowToFavorite(SqlRowSet rs) {
		Favorite favorite = new Favorite();
		favorite.setRestaurantId(rs.getLong("favorite_api_id"));
		favorite.setRestaurantName(rs.getString("favorite_name"));
		favorite.setCuisine(rs.getString("cuisine"));
		favorite.setPhoneNumber(rs.getString("phone_number"));
		favorite.setRating(rs.getString("ratings"));
		favorite.setDescription(rs.getString("description"));
		favorite.setFeaturedImage(rs.getString("featured_image"));
		favorite.setCurrency(rs.getString("currency"));
		favorite.setMenuUrl(rs.getString("menu_url"));
		favorite.setUrl(rs.getString("url"));
		return favorite;
	}
}
