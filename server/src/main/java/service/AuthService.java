package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;

public class AuthService {
    private final AuthDAO dataAccess;
    public AuthService(AuthDAO dataAccess){
        this.dataAccess=dataAccess;
    }
    public void clear() throws DataAccessException {
        dataAccess.clear();

    }
}
