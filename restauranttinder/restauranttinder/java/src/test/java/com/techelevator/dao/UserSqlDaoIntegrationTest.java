package com.techelevator.dao;

import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.*;

public class UserSqlDaoIntegrationTest extends DAOIntegrationTest {

    private UserSqlDAO userSqlDAO;
    private User testUser;

    @Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        userSqlDAO = new UserSqlDAO(jdbcTemplate);
        userSqlDAO.create("TEST_USER_2","test_password","user", "19147", 39.935331,-75.152585);
        testUser = userSqlDAO.findByUsername("TEST_USER_2");
    }

    @Test
    public void createNewUser() {
        boolean userCreated = userSqlDAO.create("TEST_USER","test_password","user", "19147", 39.935331, -75.152585);
        Assert.assertTrue(userCreated);
        User user = userSqlDAO.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

    @Test
    public void testFindIdByUsername() {
        long testUserId = testUser.getId();
        testUserId = (int) testUserId;
        int idFromDatabase = userSqlDAO.findIdByUsername("TEST_USER_2");

        assertEquals(testUserId, idFromDatabase);
    }

    @Test
    public void testGetUserById() {
        User userFromDatabase = userSqlDAO.getUserById(testUser.getId());
        assertEquals(testUser.getUsername(), userFromDatabase.getUsername());
    }

    @Test
    public void testFindAll() {
        List<User> userList = userSqlDAO.findAll();
        int userCount = userList.size();

        assertEquals(testUser.getUsername(), userList.get(userList.size()-1).getUsername());

        userSqlDAO.create("TEST_USER_3","test_password","user", "19147", 39.935331,-75.152585);

        userList = userSqlDAO.findAll();
        int secondCount = userList.size();

        assertTrue(secondCount > userCount);
        assertEquals("TEST_USER_3", userList.get(userList.size()-1).getUsername());
    }

    @Test
    public void testFindByUserName() {
        User userFromDB = userSqlDAO.findByUsername(testUser.getUsername());
        assertEquals("TEST_USER_2", userFromDB.getUsername());
    }

    @Test
    public void testCreate() {
        assertTrue(userSqlDAO.create("TEST_USER_3","test_password","user", "19147", 39.935331,-75.152585));
    }

}
