package dataaccess;
import model.*;
import server.JoinGameRequest;

import java.util.Collection;

public interface GameDAO {
    void clear() throws DataAccessException;
    GameData getGame(int ID) throws DataAccessException;
    void createGame(GameData game) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    void updateGame(int ID) throws DataAccessException;
    void joinGame(JoinGameRequest request) throws DataAccessException;
}
