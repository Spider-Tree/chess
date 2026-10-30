package dataaccess;

import model.AuthData;
import service.*;

import java.util.HashMap;

public interface AuthDAO {
    void clear() throws DataAccessException;
    void createAuth(AuthData authdata) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    LogoutResult deleteAuth() throws DataAccessException;
    void updateAuth(AuthData authdata) throws DataAccessException;
    AuthData getAuthfromUsername(String username) throws DataAccessException;
    HashMap<String,AuthData> getMemory();
}
