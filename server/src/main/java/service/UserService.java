package service;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import server.LoginRequest;

//import java.util.Collection;
public record UserService(UserDAO dataAccess) {

    public void clear() throws DataAccessException {
        dataAccess.clear();


    }

    public UserData addUser(UserData user) throws DataAccessException {
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        dataAccess.createUser(new UserData(user.username(),hashedPassword, user.email()));

        return dataAccess.getUser(user.username());

    }

    public UserData loginUser(LoginRequest request) throws DataAccessException {
        UserData user = dataAccess.getUser(request.username());

        if (user == null) {
            throw new DataAccessException(DataAccessException.Code.UNAUTHORIZED_ERROR, "Error: User does not exist");
        }
        if (!BCrypt.checkpw(request.password(), user.password())) {
            throw new DataAccessException(DataAccessException.Code.UNAUTHORIZED_ERROR, "Error: Wrong Password");
        }
        return user;
    }
}
