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
    public static void initialization(){
         testUserData = new UserData("testUser","hashedPassword","testing@gmail.com");
    }
    @BeforeEach
    public void setup() throws DataAccessException {
        testUserDAO = new MySQLUserDAO();
        testUserDAO.clear();
    }


    @Test
    void clear() throws DataAccessException {

        testUserService.clear();
        assertThrows(DataAccessException.class, () -> {
            testUserService.loginUser(new LoginRequest(testUserData.username(), testUserData.password()));
        });

    }

    @Test
    void addUser() throws DataAccessException {
        UserData tempUser = new UserData("Sam", "123", "sam@gmail.com");
        UserData result = testUserService.addUser(tempUser);
        assertEquals(tempUser, result);

    }

    @Test
    void addMultipleUsers() {

    }

    @Test
    void alreadyTaken() throws DataAccessException {
        testUserService.addUser(new UserData("Sam", "123", "sam@gmail.com"));
        assertThrows(DataAccessException.class, () -> {
            testUserService.addUser(new UserData("Sam", "123", "sam@gmail.com"));
        });

    }

    @Test
    void loginUserExistingUser() throws DataAccessException {
        assertEquals(testUserData, testUserService.loginUser(new LoginRequest(testUserData.username(), testUserData.password())));
    }

    @Test
    void userDoesNotExist() {
        assertThrows(DataAccessException.class, () -> {
            testUserService.clear();
            testUserService.loginUser(new LoginRequest(testUserData.username(), testUserData.password()));
        });
    }

    @Test
    void wrongPassword() {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            testUserService.loginUser(new LoginRequest(testUserData.username(), "141949wrong"));
        });

        assertEquals(DataAccessException.Code.UNAUTHORIZED_ERROR, exception.getCode());
    }

}