package dataaccess;

import model.AuthData;
import service.*;

public interface AuthDAO {
    void clear() throws DataAccessException;
    LoginResult createAuth() throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    ClearResult deleteAuth() throws DataAccessException;

}
