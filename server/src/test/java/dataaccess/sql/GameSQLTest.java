package dataaccess.sql;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;
import dataaccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.JoinGameRequest;
import service.GameService;


import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GameSQLTest {
    private static GameDAO testGameDAO;
    private static GameData testGameData;
    private static GameData joinTestGameData;

    private void assertBoardSame(ChessBoard expected, ChessBoard actual) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);

                assertEquals(expected.getPiece(position), actual.getPiece(position));
            }
        }
    }
    @BeforeAll
    public static void initialization(){
        testGameData = new GameData(12345678,"whiteTestUser","blackTestUser","testGame",new ChessGame());
        joinTestGameData = new GameData(12345812,null,null,"joinable",new ChessGame());
    }
    @BeforeEach
    public void setup() throws DataAccessException {
        testGameDAO = new MySQLGameDAO();
        testGameDAO.clear();
    }

    @Test
    void clear() throws DataAccessException {
        testGameDAO.createGame(testGameData);
        testGameDAO.clear();
        assertNull(testGameDAO.getGame(testGameData.gameID()));
    }

    @Test
    void listGames() throws DataAccessException {
        testGameDAO.createGame(testGameData);
        Collection<GameData> games = testGameDAO.listGames();
        assertEquals(1, games.size());
        assertTrue(games.contains(testGameData));
    }

    @Test
    void listGamesEmpty() throws DataAccessException {
        Collection<GameData> games = testGameDAO.listGames();
        assertNotNull(games);
        assertTrue(games.isEmpty());
    }

    @Test
    void getGame() throws DataAccessException {
        testGameDAO.getGame(testGameData.gameID());
        GameData testResult = testGameDAO.getGame(testGameData.gameID());
        assertEquals(testGameData, testResult);
    }

    @Test
    void getGameNonexistent() throws DataAccessException {
        GameData testResult = testGameDAO.getGame(44987124);
        assertNull(testResult);
    }

    @Test
    void addGame() throws DataAccessException {
        testGameDAO.createGame(joinTestGameData);
        GameData testResult = testGameDAO.getGame(joinTestGameData.gameID());
        assertAll("Correct GameInformation",
                () -> assertEquals(joinTestGameData.gameName(), testResult.gameName()),
                () -> assertNull(testResult.blackUsername()),
                () -> assertNull(testResult.whiteUsername()),
                () -> assertNotNull(testResult.game())

        );
    }




}