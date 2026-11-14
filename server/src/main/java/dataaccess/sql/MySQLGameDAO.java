package dataaccess.sql;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;
import server.JoinGameRequest;
import chess.ChessGame;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class MySQLGameDAO implements GameDAO {
    public MySQLGameDAO() throws DataAccessException {
        DatabaseManager.configureDatabase(createStatements);
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE game";
        DatabaseManager.executeUpdate(statement);
    }

    @Override
    public GameData getGame(int gameId) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, gameData FROM game WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            } catch (Exception e) {
                throw new DataAccessException(DataAccessException.Code.SERVER_ERROR, "Error: Unable to read data");
            }
        } catch (SQLException e) {
            throw new DataAccessException(DataAccessException.Code.SERVER_ERROR, "Error: Unable to connect");
        }
        return null;
    }

    @Override
    public void createGame(GameData game) throws DataAccessException {
        var statement = "INSERT INTO game (gameID,gameData) VALUES (?, ?)";
        String json = new Gson().toJson(game);
        DatabaseManager.executeUpdate(statement, game.gameID(), json);

    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        ArrayList<GameData> total = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, gameData FROM game";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        total.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(DataAccessException.Code.SERVER_ERROR, "Error: Unable to read games");
        }
        return total;
    }

    @Override
    public void joinGame(JoinGameRequest request) throws DataAccessException {
        GameData game = getGame(request.gameID());
        var statement = "UPDATE game SET gameData = ? WHERE gameID = ?";
        if (request.color() == ChessGame.TeamColor.WHITE) {
            GameData updated =
                    new GameData(
                            request.gameID(),
                            request.username(),
                            game.blackUsername(),
                            game.gameName(),
                            game.game());
            DatabaseManager.executeUpdate(statement, new Gson().toJson(updated), request.gameID());
        } else {
            GameData updated =
                    new GameData(
                            request.gameID(),
                            game.whiteUsername(),
                            request.username(),
                            game.gameName(),
                            game.game());
            DatabaseManager.executeUpdate(statement, new Gson().toJson(updated), request.gameID());
        }
    }

    public void updateGame(GameData game) throws DataAccessException{
        var statement = "UPDATE game SET gameData = ? WHERE gameID = ?";
        String json = new Gson().toJson(game);
        DatabaseManager.executeUpdate(statement,json,game.gameID());
    }
    private GameData readGame(ResultSet rs) throws SQLException {
        var json = rs.getString("gameData");
        GameData game = new Gson().fromJson(json, GameData.class);
        return game;
    }



    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
              `gameID` int NOT NULL,
              `gameData` JSON NOT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


}
