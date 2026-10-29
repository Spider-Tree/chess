package service;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.*;

import java.util.Collection;
public class UserService {
    private final UserDAO dataAccess;
    public UserService(UserDAO dataAccess){
        this.dataAccess=dataAccess;
    }

    public void clearAll() throws DataAccessException{
        dataAccess.clear();

    }
}
