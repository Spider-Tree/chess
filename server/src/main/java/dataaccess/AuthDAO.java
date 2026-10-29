package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void clear() throws DataAccessException;
    void createAuth() throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    void deleteAuth() throws DataAccessException;

}
