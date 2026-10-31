package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;

import java.util.UUID;

public record AuthService(AuthDAO dataAccess) {

    public void clear() throws DataAccessException {
        dataAccess.clear();

    }

    public AuthData createAuth(String username) throws DataAccessException {

        String authToken = UUID.randomUUID().toString();
        dataAccess.createAuth(new AuthData(authToken, username));
        return dataAccess.getAuth(authToken);
    }

    public void addAuth(AuthData auth) throws DataAccessException {
        dataAccess.createAuth(auth);
    }

    public AuthData getAuthData(String authToken) throws DataAccessException {
        return dataAccess.getAuth(authToken);
    }

    public void logout(String token) throws DataAccessException {
        AuthData auth = dataAccess.getAuth(token);
        if (auth == null) {
            throw new DataAccessException(DataAccessException.Code.UNAUTHORIZED_ERROR, "Error: unauthorized");
        }
        dataAccess.deleteAuth(token);
    }
}
