package service;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.GameData;

import java.util.Collection;


public class GameService {
    private final GameDAO dataAccess;
    public GameService(GameDAO dataAccess){
        this.dataAccess=dataAccess;
    }
    public void clear() throws DataAccessException {
        dataAccess.clear();

    }
    public Collection<GameData> listGames() throws DataAccessException {

        return dataAccess.listGames();
    }
}
