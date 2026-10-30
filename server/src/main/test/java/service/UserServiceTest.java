package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private static UserService testUserService;

    @BeforeAll
    public static void init(){
        testUserService=new UserService(new MemoryUserDAO());

    }
    @BeforeEach
    public void setup() throws DataAccessException {
    testUserService.clear();
    testUserService.addUser(new UserData("testUser","123test","testingUser.gmail.com"));
    }
    @Test
    public void keepsDataStored(){
        assertFalse(testUserService.getDataAccess().getMemory().isEmpty());
    }

    @Test
    void clear() throws DataAccessException {

        testUserService.clear();
        UserDAO testDAO=testUserService.getDataAccess();
        assertTrue(testDAO.getMemory().isEmpty());

    }

    @Test
    void addUser() throws DataAccessException {
        UserData tempUser=new UserData("Sam","123","sam@gmail.com");
        testUserService.addUser(tempUser);
        assertEquals(testUserService.getDataAccess().getUser("Sam"),tempUser);

    }
    @Test
    void alreadyTaken(){

        assertThrows(DataAccessException.class,() ->{
            testUserService.addUser(new UserData("Sam","123","sam@gmail.com"));
            testUserService.addUser(new UserData("Sam","123","sam@gmail.com"));

        });
    }
}