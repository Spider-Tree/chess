package dataaccess;

import model.AuthData;


import java.util.HashMap;

public interface AuthDAO {
    void clear() throws DataAccessException;

    void createAuth(AuthData authdata) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    HashMap<String, AuthData> getMemory();
}
