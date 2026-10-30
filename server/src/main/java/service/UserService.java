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

    public UserData loginUser(LoginRequest request) throws DataAccessException {
        UserData user =dataAccess.getUser(request.username());
        if (user==null){
            throw new DataAccessException(DataAccessException.Code.UNAUTHORIZED_ERROR,"Error: User does not exist");
        }
        if (!user.password().equals(request.password())){
            throw new DataAccessException(DataAccessException.Code.UNAUTHORIZED_ERROR,"Error: Wrong Password");
        }
        return user;
    }
}
