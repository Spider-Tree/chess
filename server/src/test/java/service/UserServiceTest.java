package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import server.LoginRequest;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private static UserService testUserService;
    private static UserData testUserData;

    @BeforeAll
    public static void init() {
        testUserService = new UserService(new MemoryUserDAO());

        testUserData = new UserData("testUser", "123test", "testingUser@gmail.com");

    }

    @BeforeEach
    public void setup() throws DataAccessException {
        testUserService.clear();
        testUserService.addUser(testUserData);
    }

    @Test
    public void keepsDataStored() {
        assertFalse(testUserService.dataAccess().getMemory().isEmpty());
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

        assertTrue(BCrypt.checkpw(tempUser.password(), result.password()));
        assertEquals(tempUser.email(), result.email());
        assertEquals(tempUser.username(), result.username());

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
        UserData loginResult=testUserService.loginUser(new LoginRequest(testUserData.username(), testUserData.password()));

        assertTrue(BCrypt.checkpw(testUserData.password(), loginResult.password()));
        assertEquals(testUserData.email(), loginResult.email());
        assertEquals(testUserData.username(), loginResult.username());

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