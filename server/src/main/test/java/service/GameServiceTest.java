package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryGameDAO;
import model.GameData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.JoinGameRequest;


import java.util.Collection;

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
        testGameService.addGame(testGameData);
    }
    @Test
    void clear() throws DataAccessException {
        testGameService.clear();
        assertNull(testGameService.getGame(testGameData.gameID()));
    }

    @Test
    void listGames() throws DataAccessException {
        Collection<GameData> games=testGameService.listGames();
        assertEquals(1,games.size());
        assertTrue(games.contains(testGameData));
    }
    @Test
    void listGamesEmpty() throws DataAccessException{
        testGameService.clear();
        Collection<GameData> games =testGameService.listGames();
        assertNotNull(games);
        assertTrue(games.isEmpty());
    }

    @Test
    void getGame() throws DataAccessException{
        GameData testResult=testGameService.getGame(testGameData.gameID());
        assertEquals(testGameData,testResult);
    }
    @Test
    void getGameNonexistent() throws DataAccessException {
        GameData testResult = testGameService.getGame(44987124);
        assertNull(testResult);
    }
    @Test
    void createGame() throws DataAccessException {
        String testgameName="testNewGame";
        GameData testResult = testGameService.createGame(testgameName);
        assertNotNull(testResult);
        assertTrue(testResult.gameID()>=10000000);
        assertTrue(testResult.gameID()<=99999999);

        assertAll("Correct GameInformation",
                ()-> assertEquals(testgameName,testResult.gameName()),
                ()-> assertNull(testResult.blackUsername()),
                ()-> assertNull(testResult.whiteUsername()),
                ()-> assertNotNull(testResult.game())

        );
    }
    @Test
    void joinGameWhite() throws DataAccessException{
        GameData openTestGame = new GameData(4321,null,null,"openTestGame",new ChessGame());
        testGameService.addGame(openTestGame);
        JoinGameRequest request=new JoinGameRequest(ChessGame.TeamColor.WHITE,openTestGame.gameID(),"whiteUser");
        testGameService.joinGame(request);
        GameData testResult =testGameService.getGame(openTestGame.gameID());

        assertEquals(request.username(),testResult.whiteUsername());
        assertNull(testResult.blackUsername());
    }
    @Test
    void joinGameWhiteTaken() throws DataAccessException{
        JoinGameRequest request=new JoinGameRequest(ChessGame.TeamColor.WHITE,testGameData.gameID(),"whiteUser");
        DataAccessException exception = assertThrows(DataAccessException.class,()-> testGameService.joinGame(request));
        assertEquals(DataAccessException.Code.ALREADY_TAKEN_ERROR,exception.getCode());
    }
    @Test
    void joinGameBlack() throws DataAccessException{
        GameData openTestGame = new GameData(4321,null,null,"openTestGame",new ChessGame());
        testGameService.addGame(openTestGame);
        JoinGameRequest request=new JoinGameRequest(ChessGame.TeamColor.BLACK,openTestGame.gameID(),"blackUser");
        testGameService.joinGame(request);
        GameData testResult =testGameService.getGame(openTestGame.gameID());

        assertEquals(request.username(),testResult.blackUsername());
        assertNull(testResult.whiteUsername());
    }
    @Test
    void joinGameBlackTaken() throws DataAccessException{
        JoinGameRequest request=new JoinGameRequest(ChessGame.TeamColor.BLACK,testGameData.gameID(),"blackUser");
        DataAccessException exception = assertThrows(DataAccessException.class,()-> testGameService.joinGame(request));
        assertEquals(DataAccessException.Code.ALREADY_TAKEN_ERROR,exception.getCode());
    }


}