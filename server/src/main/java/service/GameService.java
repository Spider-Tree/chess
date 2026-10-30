package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.GameData;

import java.util.Collection;
import java.util.Random;


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
    public GameData createGame(String gameName) throws DataAccessException{
        int gameID= new Random().nextInt(90000000)+10000000;

        dataAccess.createGame(new GameData(gameID,"","",gameName,new ChessGame()));
        return dataAccess.getGame(gameID);

    }
}
