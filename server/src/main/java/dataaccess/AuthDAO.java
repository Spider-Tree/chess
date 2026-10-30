package dataaccess;

import model.AuthData;
import service.*;

public interface AuthDAO {
    void clear() throws DataAccessException;
    void createAuth(AuthData authdata) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    ClearResult deleteAuth() throws DataAccessException;

}
