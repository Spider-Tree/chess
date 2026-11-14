package dataaccess.sql;

import chess.*;
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
    public static void initialization() {
        testGameData = new GameData(12345678, "whiteTestUser", "blackTestUser", "testGame", new ChessGame());
        joinTestGameData = new GameData(12345812, null, null, "joinable", new ChessGame());
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
        testGameDAO.createGame(testGameData);
        GameData testResult = testGameDAO.getGame(testGameData.gameID());
        assertNotNull(testResult);
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

    @Test
    void initialBoardStores() throws DataAccessException {
        ChessGame baseGame = new ChessGame();
        GameData game = new GameData(1234, null, null, "Initial Board Test", baseGame);

        testGameDAO.createGame(game);

        GameData restored = testGameDAO.getGame(1234);

        assertNotNull(restored);
        assertNotNull(restored.game());
        assertBoardSame(baseGame.getBoard(), restored.game().getBoard());
        assertEquals(ChessGame.TeamColor.WHITE, restored.game().getTeamTurn());
        assertEquals(baseGame, restored.game());
    }

    @Test
    void movesStored() throws Exception {
        ChessGame chessGame = new ChessGame();
        GameData game = new GameData(1234, "whiteUser", "blackUser", "testGame3", chessGame);
        testGameDAO.createGame(game);

        chessGame.makeMove(new ChessMove(new ChessPosition(2, 5), new ChessPosition(4, 5), null));
        chessGame.makeMove(new ChessMove(new ChessPosition(7, 5), new ChessPosition(5, 5), null));
        testGameDAO.updateGame(game);

        GameData restoredGame = testGameDAO.getGame(1234);

        assertNotNull(restoredGame);
        assertBoardSame(chessGame.getBoard(), restoredGame.game().getBoard());
        assertEquals(chessGame, restoredGame.game());
        assertEquals(ChessGame.TeamColor.WHITE, restoredGame.game().getTeamTurn());

    }

    @Test
    void kingPositionStored() throws DataAccessException {
        ChessBoard board = new ChessBoard();
        board.addPiece(new ChessPosition(3, 4), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
        board.addPiece(new ChessPosition(8, 5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        ChessGame chessGame = new ChessGame(board, true);

        GameData game = new GameData(1234, null, null, "King Test", chessGame);
        testGameDAO.createGame(game);
        GameData restored = testGameDAO.getGame(1234);

        assertEquals(new ChessPosition(3, 4), restored.game().getKingPosition(ChessGame.TeamColor.WHITE));
        assertEquals(new ChessPosition(8, 5), restored.game().getKingPosition(ChessGame.TeamColor.BLACK));
    }


}