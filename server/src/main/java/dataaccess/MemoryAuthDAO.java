package dataaccess;

import model.AuthData;
import service.LogoutResult;

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
    public LogoutResult deleteAuth() throws DataAccessException {
        return null;
    }
    @Override
    public HashMap<String,AuthData> getMemory(){
        return auths;
    }
    @Override
    public void updateAuth(AuthData auth) throws DataAccessException{
        if(auths.get(auth.authToken())==null){
            throw new DataAccessException(DataAccessException.Code.SERVER_ERROR,"Error: Can't Update non-existent user");
        }
        auths.replace(auth.authToken(),auth);
    };
    @Override
    public AuthData getAuthfromUsername(String username) throws DataAccessException{
        return null;
    };
}
