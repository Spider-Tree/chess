package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import java.util.UUID;

public class AuthService {
    private final AuthDAO dataAccess;
    public AuthService(AuthDAO dataAccess){
        this.dataAccess=dataAccess;
    }
    public void clear() throws DataAccessException {
        dataAccess.clear();

    }
    public AuthData createAuth(String username) throws DataAccessException {
        String authToken= UUID.randomUUID().toString();
        dataAccess.createAuth(new AuthData(authToken,username));
        return dataAccess.getAuth(authToken);
    }
}
