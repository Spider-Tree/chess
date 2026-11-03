package dataaccess;

import model.GameData;
import server.JoinGameRequest;

import java.util.Collection;
import java.util.List;

public class MySQLGameDAO implements GameDAO{
    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public GameData getGame(int gameId) throws DataAccessException {
        return null;
    }

    @Override
    public void createGame(GameData game) throws DataAccessException {

    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void joinGame(JoinGameRequest request) throws DataAccessException {

    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
              `username` varchar(256) COLLATE utf8mb4_0900_as_cs NOT NULL,
              `authToken` varchar(256) NOT NULL,
              PRIMARY KEY (`username`),
              UNIQUE (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
