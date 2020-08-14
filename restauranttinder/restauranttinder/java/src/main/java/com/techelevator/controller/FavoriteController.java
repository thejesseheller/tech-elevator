package com.techelevator.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.dao.FavoriteDAO;
import com.techelevator.dao.PreferenceDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.Favorite;
import com.techelevator.model.Preference;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class FavoriteController {

    private FavoriteDAO favoriteDAO;
    private PreferenceDAO preferenceDAO;

    public FavoriteController(UserDAO userDAO, FavoriteDAO favoriteDAO, PreferenceDAO preferenceDAO) {
        this.favoriteDAO = favoriteDAO;
        this.preferenceDAO = preferenceDAO;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "users/{userId}/favorites", method = RequestMethod.GET)
    public List<Favorite> getAllFavorites(@PathVariable long userId) {
    	return favoriteDAO.getAllFavorites(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "preferences", method = RequestMethod.GET)
    public List<Preference> getAllPreferences() {
    	return preferenceDAO.getAllPreferences();
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "users/{userId}/favorites", method = RequestMethod.POST)
    public void addToFavorites(@PathVariable long userId, @RequestBody Favorite favorite) {
    	System.out.println(favorite.getRestaurantName());
    	System.out.println(userId);
    	favoriteDAO.addToFavorites(userId, favorite);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "users/{userId}/favorites/{resId}", method = RequestMethod.DELETE)
    public void removeFromFavorites(@PathVariable long userId, @PathVariable long resId) {
        favoriteDAO.removeFromFavorites(userId, resId);
    }
}



















