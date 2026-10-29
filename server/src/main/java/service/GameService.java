package service;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;


public class GameService {
    private final GameDAO dataAccess;
    public GameService(GameDAO dataAccess){
        this.dataAccess=dataAccess;
    }
    public void clear() throws DataAccessException {
        dataAccess.clear();

    }
}
