package dataaccess;

import model.AuthData;
import model.UserData;
import service.ClearResult;
import service.LoginResult;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{
    final private HashMap<Integer, AuthData> auths =new HashMap<>();

    @Override
    public void clear() throws DataAccessException {
        auths.clear();
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
