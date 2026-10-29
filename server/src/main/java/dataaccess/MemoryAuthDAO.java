package dataaccess;

import model.AuthData;
import service.ClearResult;
import service.LoginResult;

public class MemoryAuthDAO implements AuthDAO{
    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public LoginResult createAuth() throws DataAccessException {
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public ClearResult deleteAuth() throws DataAccessException {
        return null;
    }
}
