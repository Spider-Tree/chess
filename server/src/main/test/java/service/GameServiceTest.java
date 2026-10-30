package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    private static GameService testGameService;
    private static GameData testGameData;

    @BeforeAll
    public static void init(){
        testGameService=new GameService(new MemoryGameDAO());
        testGameData=new GameData(1234,"testUserWhite","testUserBlack","testGame",new ChessGame());
    }
    @BeforeEach
    public void setup() throws DataAccessException {
        testGameService.clear();

    }
    @Test
    void clear() {
    }

    @Test
    void listGames() {
    }
}