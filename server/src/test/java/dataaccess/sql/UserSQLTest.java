package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.MySQLAuthDAO;
import dataaccess.MySQLUserDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.LoginRequest;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserSQLTest {
    private static UserDAO testUserDAO;
    private static UserData testUserData;

    @BeforeAll
    public static void initialization() {
        testUserData = new UserData("testUser", "hashedPassword", "testing@gmail.com");
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        testUserDAO = new MySQLUserDAO();
        testUserDAO.clear();
    }


    @Test
    void clear() throws DataAccessException {
        testUserDAO.createUser(testUserData);
        testUserDAO.clear();

        assertNull(testUserDAO.getUser(testUserData.username()));
    }

    @Test
    void addUser() throws DataAccessException {
        testUserDAO.createUser(testUserData);
        UserData result = testUserDAO.getUser(testUserData.username());
        assertEquals(testUserData, result);

    }


    @Test
    void alreadyTaken() throws DataAccessException {
        testUserDAO.createUser(testUserData);
        assertThrows(DataAccessException.class, () -> {
            testUserDAO.createUser(testUserData);
        });

    }

    @Test
    void userDoesNotExist() throws DataAccessException {
        assertNull(testUserDAO.getUser("DoesnexisitJohanthan"));
    }


}