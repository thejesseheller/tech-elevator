package com.techelevator.dao;

import com.techelevator.model.Favorite;

import java.util.List;

public interface FavoriteDAO {

    void addToFavorites(Long userId, Favorite favorite);

    void removeFromFavorites(Long userId, Long resId);

    List<Favorite> getAllFavorites(Long userId);

    boolean existsInFavorites(Favorite favorite);

    boolean existsInUserFavorite(Long userId, Favorite favorite);

}
