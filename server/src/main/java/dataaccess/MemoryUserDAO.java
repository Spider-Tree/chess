package dataaccess;
import chess.ChessGame;
import model.*;
import java.util.HashMap;
public class MemoryUserDAO implements UserDAO{
    final private HashMap<String, UserData> users =new HashMap<>();

    @Override
    public void clear(){
        users.clear();

    }

    @Override
    public void createUser(UserData userdata) throws DataAccessException {
        if (null!=users.get(userdata.username())){
            throw new DataAccessException(DataAccessException.Code.ALREADY_TAKEN_ERROR,"Error: Username already exists");
        }
        users.put(userdata.username(),userdata);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);

    }
    @Override
    public HashMap<String,UserData> getMemory(){
        return users;
    }


}
