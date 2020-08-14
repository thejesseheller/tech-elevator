package com.techelevator.dao;

import com.techelevator.model.Favorite;
import com.techelevator.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.*;

public class FavoriteSqlDaoIntegrationTest extends DAOIntegrationTest {

    private FavoriteSqlDAO favoriteDAO;
    private UserSqlDAO userSqlDAO;
    private User testUser;

    @Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        favoriteDAO = new FavoriteSqlDAO(jdbcTemplate);
        userSqlDAO = new UserSqlDAO(jdbcTemplate);
        userSqlDAO.create("TEST_USER_2","test_password","user", "19147", 39.935331,-75.152585);
        testUser = userSqlDAO.findByUsername("TEST_USER_2");
    }


    @Test
    public void testGetAllFavorites() {
        Favorite testFavorite = new Favorite();
        testFavorite.setRestaurantId(1L);
        testFavorite.setRestaurantName("Test Restaurant");

        favoriteDAO.addToFavorites(testUser.getId(), testFavorite);

        List<Favorite> testList = favoriteDAO.getAllFavorites(testUser.getId());
        assertEquals(1, testList.size());
        assertEquals(testFavorite, testList.get(0));
    }

    @Test
    public void testExistsInFavorites() {
        Favorite testFavorite = new Favorite();
        testFavorite.setRestaurantId(1L);
        testFavorite.setRestaurantName("Test Restaurant");

        assertFalse(favoriteDAO.existsInFavorites(testFavorite));

        favoriteDAO.addToFavorites(testUser.getId(), testFavorite);

        assertTrue(favoriteDAO.existsInFavorites(testFavorite));
    }

    @Test
    public void testExistsInUserFavorite() {
        Favorite testFavorite = new Favorite();
        testFavorite.setRestaurantId(1L);
        testFavorite.setRestaurantName("Test Restaurant");

        assertFalse(favoriteDAO.existsInUserFavorite(testUser.getId(), testFavorite));

        favoriteDAO.addToFavorites(testUser.getId(), testFavorite);

        assertTrue(favoriteDAO.existsInUserFavorite(testUser.getId(), testFavorite));

    }

    @Test
    public void testAddToFavorites() {
        Favorite testFavorite = new Favorite();
        testFavorite.setRestaurantId(1L);
        testFavorite.setRestaurantName("Test Restaurant");

        favoriteDAO.addToFavorites(testUser.getId(), testFavorite);
        List<Favorite> testUserFavorites = favoriteDAO.getAllFavorites(testUser.getId());
        assertEquals(testFavorite, testUserFavorites.get(0));
    }

    @Test
    public void testRemoveFromFavorites() {
        Favorite testFavorite = new Favorite();
        testFavorite.setRestaurantId(1L);
        testFavorite.setRestaurantName("Test Restaurant");

        favoriteDAO.addToFavorites(testUser.getId(), testFavorite);
        List<Favorite> testUserFavorites = favoriteDAO.getAllFavorites(testUser.getId());
        assertEquals(testFavorite, testUserFavorites.get(0));

        favoriteDAO.removeFromFavorites(testUser.getId(), testFavorite.getRestaurantId());
        testUserFavorites = favoriteDAO.getAllFavorites(testUser.getId());
        assertTrue(testUserFavorites.isEmpty());

    }
}
