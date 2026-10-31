package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class that can manage a chess game, making moves on a board
 *
 * <p>Note: You can add to this class, but you may not alter signature of the existing methods.
 */
public class ChessGame {
    private int turnCounter = 1;
    private boolean teamTurn = true;
    private ChessBoard currentBoard;
    // Index 0 is the white king; index 1 is the black king.
    private ChessPosition[] kingLookup = {new ChessPosition(1, 5), new ChessPosition(8, 5)};

    public ChessGame() {
        this.currentBoard = new ChessBoard();
        currentBoard.resetBoard();
        kingLookup[0] = new ChessPosition(1, 5);
        kingLookup[1] = new ChessPosition(8, 5);
    }

    public ChessGame(ChessBoard board, boolean teamTurn) {
        this.teamTurn = teamTurn;
        this.currentBoard = board;
        updateKingPositions();
    }

    public ChessGame(ChessBoard board, boolean teamTurn, ChessPosition[] lookup) {
        this.teamTurn = teamTurn;
        this.currentBoard = board;
        this.kingLookup = lookup;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        if (teamTurn) {
            return TeamColor.WHITE;
        } else {
            return TeamColor.BLACK;
        }
    }

    public ChessPosition getKingPosition(TeamColor color) {
        if (color == TeamColor.WHITE) {
            return kingLookup[0];
        } else {
            return kingLookup[1];
        }
    }

    public void setKingPosition(TeamColor color, ChessPosition pos) {
        if (color == TeamColor.WHITE) {
            kingLookup[0] = pos;
        } else {
            kingLookup[1] = pos;
        }
    }

    /**
     * Sets which team's turn it is.
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        if (team == TeamColor.WHITE) {
            teamTurn = true;
        } else if (team == TeamColor.BLACK) {
            teamTurn = false;
        } else {
            System.out.println("Something broke");
        }
    }

    /** Enum identifying the 2 possible teams in a chess game */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location.
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        TeamColor color = currentBoard.getPiece(startPosition).getTeamColor();
        ArrayList<ChessMove> allMoves = new ArrayList<>();
        ArrayList<ChessMove> vMoves = new ArrayList<>();
        allMoves.addAll(
                currentBoard.getPiece(startPosition).pieceMoves(currentBoard, startPosition));
        ChessPosition actualPos = new ChessPosition(getKingPosition(color));
        ChessBoard currentGameState = new ChessBoard(currentBoard);

        for (ChessMove move : allMoves) {
            currentBoard = new ChessBoard(currentGameState);
            if (currentBoard.getPiece(move.getStartPosition()).getPieceType()
                    == ChessPiece.PieceType.KING) {
                currentBoard.makeMove(move);
                setKingPosition(color, move.getEndPosition());
            } else {
                currentBoard.makeMove(move);
            }

            if (!this.isInCheck(color)) {
                vMoves.add(move);
            }
        }
        currentBoard = currentGameState;
        setKingPosition(color, actualPos);

        return vMoves;
    }

    /**
     * Makes a move in the chess game.
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (null == currentBoard.getPiece(move.getStartPosition())) {
            throw new InvalidMoveException();
        }
        if (getTeamTurn() != currentBoard.getPiece(move.getStartPosition()).getTeamColor()) {
            throw new InvalidMoveException();
        }
        Collection<ChessMove> potential = validMoves(move.getStartPosition());
        boolean valid = false;
        for (ChessMove m : potential) {
            if (m.equals(move)) {
                valid = true;
                break;
            }
        }
        if (valid) {
            if (currentBoard.getPiece(move.getStartPosition()).getTeamColor() == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            } else {
                setTeamTurn(TeamColor.WHITE);
            }
            currentBoard.makeMove(move);
            turnCounter += 1;
        } else {
            throw new InvalidMoveException();
        }
    }

    /**
     * Determines if the given team is in check.
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ArrayList<ChessMove> total = new ArrayList<>();
        for (int r = 1; r < 9; r++) {
            for (int c = 1; c < 9; c++) {
                ChessPosition position = new ChessPosition(r, c);
                ChessPiece piece = currentBoard.getPiece(position);
                if (piece != null && teamColor != piece.getTeamColor()) {
                    total.addAll(piece.pieceMoves(currentBoard, position));
                }
            }
        }

        for (ChessMove move : total) {
            if (this.getKingPosition(teamColor).equals(move.getEndPosition())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets all valid moves for the given team.
     *
     * @param teamColor the team to get moves for
     * @return All valid moves for the specified team
     */
    public Collection<ChessMove> getAllValid(TeamColor teamColor) {
        ArrayList<ChessMove> totalValid = new ArrayList<>();
        for (int r = 1; r < 9; r++) {
            for (int c = 1; c < 9; c++) {
                ChessPosition position = new ChessPosition(r, c);
                ChessPiece piece = currentBoard.getPiece(position);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    totalValid.addAll(this.validMoves(position));
                }
            }
        }
        return totalValid;
    }

    public boolean isInCheckmate(TeamColor teamColor) {
        ArrayList<ChessMove> valid = new ArrayList<>();
        valid.addAll(getAllValid(teamColor));
        if (valid.isEmpty() && isInCheck(teamColor)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having no valid moves
     * while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ArrayList<ChessMove> valid = new ArrayList<>();
        valid.addAll(getAllValid(teamColor));
        if (valid.isEmpty() && !isInCheck(teamColor)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets this game's chessboard to a given board.
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.currentBoard = board;
        updateKingPositions();
    }

    private void updateKingPositions() {
        for (int row = 1; row <= 8; row++) {
            for (int column = 1; column <= 8; column++) {
                ChessPosition position = new ChessPosition(row, column);
                ChessPiece piece = currentBoard.getPiece(position);
                if (piece == null || piece.getPieceType() != ChessPiece.PieceType.KING) {
                    continue;
                }
                setKingPosition(piece.getTeamColor(), position);
            }
        }
    }

    /**
     * Gets the current chessboard.
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentBoard;
    }

    @Override
    public int hashCode() {
        if (teamTurn) {
            return turnCounter + currentBoard.hashCode();
        } else {
            return turnCounter + currentBoard.hashCode() + 12;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ChessGame other = (ChessGame) o;
        if (other.currentBoard.equals(this.currentBoard)
                && (this.turnCounter == other.turnCounter)
                && this.teamTurn == other.teamTurn) {
            return true;
        }
        return false;
    }
}