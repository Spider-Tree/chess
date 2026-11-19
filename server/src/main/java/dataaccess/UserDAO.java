package dataaccess;

import dataaccess.exception.DataAccessException;
import model.UserData;

import java.util.HashMap;

public interface UserDAO {
    void clear() throws DataAccessException;

    void createUser(UserData userdata) throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    HashMap<String, UserData> getMemory();
}
