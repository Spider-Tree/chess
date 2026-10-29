package dataaccess;
import model.*;
import service.*;

public interface GameDAO {
    void clear() throws DataAccessException;
    GameData getGame(int ID) throws DataAccessException;
    void createGame() throws DataAccessException;
    ListGamesResult  listGames() throws DataAccessException;
    void updateGame(int ID) throws DataAccessException;
}
