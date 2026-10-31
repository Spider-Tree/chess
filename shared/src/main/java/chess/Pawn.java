package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn {
    private final ArrayList<ChessMove> allPos = new ArrayList<>();

    public Collection<ChessMove> pieceMovePawn(ChessBoard board, ChessPosition myPosition) {
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            moveLogicWhite(board, myPosition);
        } else {
            moveLogicBlack(board, myPosition);
        }
        return allPos;
    }

    public void moveLogicWhite(ChessBoard board, ChessPosition start) {
        addPawnMoves(board, start, ChessGame.TeamColor.WHITE, 1, 2, 7);
    }

    public void moveLogicBlack(ChessBoard board, ChessPosition start) {
        addPawnMoves(board, start, ChessGame.TeamColor.BLACK, -1, 7, 2);
    }

    private void addPawnMoves(
            ChessBoard board,
            ChessPosition start,
            ChessGame.TeamColor color,
            int direction,
            int startingRow,
            int promotionRow) {
        int row = start.getRow();
        int column = start.getColumn();
        int distance = row == startingRow ? 2 : 1;
        boolean promotes = row == promotionRow;

        for (int step = 1; step <= distance; step++) {
            ChessPosition end = new ChessPosition(row + direction * step, column);
            if (board.getPiece(end) != null) {
                break;
            }
            addMove(start, end, promotes);
        }

        for (int offset = -1; offset <= 1; offset += 2) {
            int targetColumn = column + offset;
            if (targetColumn < 1 || targetColumn > 8) {
                continue;
            }
            ChessPosition end = new ChessPosition(row + direction, targetColumn);
            ChessPiece target = board.getPiece(end);
            if (target != null && target.getTeamColor() != color) {
                addMove(start, end, promotes);
            }
        }
    }

    private void addMove(ChessPosition start, ChessPosition end, boolean promotes) {
        if (!promotes) {
            allPos.add(new ChessMove(start, end, null));
            return;
        }
        for (ChessPiece.PieceType promotion :
                new ChessPiece.PieceType[] {
                        ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.KNIGHT,
                        ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.ROOK
                }) {
            allPos.add(new ChessMove(start, end, promotion));
        }
    }
}
