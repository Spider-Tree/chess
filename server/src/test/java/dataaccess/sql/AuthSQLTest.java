package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.MySQLAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AuthService;

import static org.junit.jupiter.api.Assertions.*;

class AuthSQLTest {
    private static AuthDAO testAuthDAO;
    private static AuthData testAuthData;

    @BeforeAll
    public static void initialization(){
         testAuthData = new AuthData("1234authtoken","testuser1");
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        testAuthDAO = new MySQLAuthDAO();
        testAuthDAO.clear();
    }

    @Test
    void clear() throws DataAccessException {
        testAuthDAO.createAuth(testAuthData);
        testAuthDAO.createAuth(new AuthData("token 2","user2"));

        testAuthDAO.clear();

        assertNull(testAuthDAO.getAuth(testAuthData.authToken()));
        assertNull(testAuthDAO.getAuth("token 2"));
    }




    @Test
    void createAuth() throws DataAccessException {
        testAuthDAO.createAuth(testAuthData);
        assertEquals(testAuthData,testAuthDAO.getAuth(testAuthData.authToken()));
    }



    @Test
    void deleteAuth() throws DataAccessException {
        testAuthDAO.createAuth(testAuthData);
        testAuthDAO.deleteAuth(testAuthData.authToken());

        assertNull(testAuthDAO.getAuth(testAuthData.authToken()));

    }

}