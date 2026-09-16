package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Queen {
    ArrayList<ChessMove> allpos;
    public Queen(){}
    public Collection<ChessMove> pieceMoveQueen(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> straightLines = new Rook().pieceMoveRook(board,myPosition);
        Collection<ChessMove> diagonals= new Bishop().pieceMoveBishop(board,myPosition);
        allpos.addAll(straightLines);
        allpos.addAll(diagonals);
        return allpos;
    }
}
