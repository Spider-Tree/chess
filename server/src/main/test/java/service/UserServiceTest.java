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
    private static UserData testUserData;

    @BeforeAll
    public static void init(){
        testUserService=new UserService(new MemoryUserDAO());
        testUserData=new UserData("testUser","123test","testingUser@gmail.com");

    }
    @BeforeEach
    public void setup() throws DataAccessException {
    testUserService.clear();
    testUserService.addUser(testUserData);
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
    @Test
    void loginUserExistingUser() throws DataAccessException {
        assertEquals(testUserData,testUserService.loginUser(new LoginRequest(testUserData.username(),testUserData.password())));
    }
    @Test
    void userDoesNotExist(){
        assertThrows(DataAccessException.class,()->{
            testUserService.clear();
            testUserService.loginUser(new LoginRequest(testUserData.username(),testUserData.password()));
        });
    }
    @Test
    void wrongPassword(){
        assertThrows(DataAccessException.class,()->{
            testUserService.loginUser(new LoginRequest(testUserData.username(),"141949wrong"));
        });
    }
    @Test
    void alreadyLoggedIn(){

    }
}