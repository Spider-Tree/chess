package service;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;

//import java.util.Collection;
public class UserService {
    private final UserDAO dataAccess;
    public UserService(UserDAO dataAccess){
        this.dataAccess=dataAccess;
    }

    public void clear() throws DataAccessException{
        dataAccess.clear();

    }
}
