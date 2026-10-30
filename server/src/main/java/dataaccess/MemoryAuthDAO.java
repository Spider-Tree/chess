package dataaccess;

import model.AuthData;
import model.UserData;
import service.ClearResult;
import service.LoginResult;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{
    final private HashMap<String, AuthData> auths =new HashMap<>();

    @Override
    public void clear() throws DataAccessException {
        auths.clear();
    }

    @Override
    public void createAuth(AuthData authdata) throws DataAccessException {
        auths.put(authdata.authToken(),authdata);

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return auths.get(authToken);
    }

    @Override
    public ClearResult deleteAuth() throws DataAccessException {
        return null;
    }
}
