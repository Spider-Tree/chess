package dataaccess;

import model.UserData;
import service.*;

public interface UserDAO {
    void clear() throws DataAccessException;
    void createUser(UserData userdata) throws AlreadyTakenException;
    UserData getUser(String username) throws DataAccessException;
}
