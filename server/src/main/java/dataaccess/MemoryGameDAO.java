package dataaccess;

import model.GameData;
import service.ListGamesResult;

public class MemoryGameDAO implements GameDAO{
    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public GameData getGame(int ID) throws DataAccessException {
        return null;
    }

    @Override
    public void createGame() throws DataAccessException {

    }

    @Override
    public ListGamesResult listGames() throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(int ID) throws DataAccessException {

    }
}
