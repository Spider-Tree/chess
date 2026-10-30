package service;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;

//import java.util.Collection;
public class UserService {
    private final UserDAO dataAccess;
    public UserService(UserDAO dataAccess){
        this.dataAccess=dataAccess;
    }

    public void clear() throws DataAccessException{
        dataAccess.clear();


    }
    public UserData addUser(UserData user) throws DataAccessException {
        dataAccess.createUser(user);

        return dataAccess.getUser(user.username());

    }
    public UserDAO getDataAccess(){
        return dataAccess;
    }
}
