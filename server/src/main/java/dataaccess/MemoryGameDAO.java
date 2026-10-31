package dataaccess;

import chess.ChessGame;

import model.GameData;

import server.JoinGameRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {

    private final HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }

    @Override
    public GameData getGame(int gameId) throws DataAccessException {
        return games.get(gameId);
    }

    @Override
    public void createGame(GameData game) throws DataAccessException {
        games.put(game.gameID(), game);
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        ArrayList<GameData> total = new ArrayList<>(games.values());
        System.out.println(total);
        return total;
    }

    @Override
    public void joinGame(JoinGameRequest request) throws DataAccessException {
        GameData game = games.get(request.gameID());
        if (request.color() == ChessGame.TeamColor.WHITE) {
            GameData updated = new GameData(request.gameID(), request.username(), game.blackUsername(), game.gameName(), game.game());
            games.replace(request.gameID(), updated);
        } else {
            GameData updated = new GameData(request.gameID(), game.whiteUsername(), request.username(), game.gameName(), game.game());
            games.replace(request.gameID(), updated);
        }

    }

}
