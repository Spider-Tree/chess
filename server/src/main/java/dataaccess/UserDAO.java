package dataaccess;

import model.AuthData;
import model.UserData;
import service.*;

import java.util.HashMap;

public interface UserDAO {
    void clear() throws DataAccessException;
    void createUser(UserData userdata) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;
    HashMap<String, UserData> getMemory();
}
