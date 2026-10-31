package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    private static AuthService testAuthService;
    private static AuthData testAuthData;

    @BeforeAll
    public static void init() {
        testAuthService = new AuthService(new MemoryAuthDAO());
        testAuthData = new AuthData("198u598-21084u1-925-a", "testUser");
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        testAuthService.clear();
        testAuthService.addAuth(testAuthData);
    }

    @Test
    void clear() throws DataAccessException {
        testAuthService.clear();
        assertNull(testAuthService.getAuthData(testAuthData.authToken()));
    }

    @Test
    void keepDataStored() throws DataAccessException {
        assertFalse(testAuthService.dataAccess().getMemory().isEmpty());
    }


    @Test
    void createAuth() throws DataAccessException {
        AuthData auth = testAuthService.createAuth("test1");
        assertEquals(auth, testAuthService.getAuthData(auth.authToken()));
    }

    @Test
    void logout() throws DataAccessException {
        testAuthService.logout(testAuthData.authToken());
        assertTrue(testAuthService.dataAccess().getMemory().isEmpty());
    }

    @Test
    void logoutWrongUser() {
        assertThrows(DataAccessException.class, () -> {
            testAuthService.logout("notCorrectToken");

        });

    }
}