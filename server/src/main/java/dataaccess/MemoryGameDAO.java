package dataaccess;

import model.GameData;
import model.UserData;
import service.ListGamesResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    final private HashMap<Integer, GameData> games =new HashMap<>();

    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }

    @Override
    public GameData getGame(int ID) throws DataAccessException {
        return null;
    }

    @Override
    public void createGame() throws DataAccessException {

    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        ArrayList<GameData> total = new ArrayList<>(games.values());
        System.out.println(total);
        return total;
    }

    @Override
    public void updateGame(int ID) throws DataAccessException {

    }
}
