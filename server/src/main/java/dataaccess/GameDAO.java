package dataaccess;
import model.*;
public interface GameDAO {
    void clear() throws DataAccessException;
    GameData getGame(int ID) throws DataAccessException;
    void createGame() throws DataAccessException;
    //listGames???
    GameData updateGame(int ID) throws DataAccessException;
}
