package chess;

import java.util.ArrayList;
import java.util.Collection;

public class King {
    private final ArrayList<ChessMove> allPositions = new ArrayList<>();

    public Collection<ChessMove> pieceMoveKing(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();

        for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
            for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
                int targetRow = row + rowOffset;
                int targetColumn = column + columnOffset;
                if (targetRow < 1 || targetRow > 8 || targetColumn < 1 || targetColumn > 8) {
                    continue;
                }
                ChessPosition end = new ChessPosition(targetRow, targetColumn);
                ChessPiece target = board.getPiece(end);
                if (target == null || target.getTeamColor() != color) {
                    allPositions.add(new ChessMove(myPosition, end, null));
                }
            }
        }
        return allPositions;
    }
}