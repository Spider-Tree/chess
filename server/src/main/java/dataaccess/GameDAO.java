package dataaccess;
import model.*;
import service.*;

import java.util.Collection;

public interface GameDAO {
    void clear() throws DataAccessException;
    GameData getGame(int ID) throws DataAccessException;
    void createGame() throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    void updateGame(int ID) throws DataAccessException;
}
