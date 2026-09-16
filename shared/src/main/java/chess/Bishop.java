package chess;


import java.util.ArrayList;
import java.util.Collection;

public class Bishop {
    ArrayList<ChessMove> allPos=new ArrayList<>();
    public Bishop() {
    }

    public Collection<ChessMove> pieceMoveBishop(ChessBoard board, ChessPosition myPosition) {
        //check each Diagonal
        checkDiagonal(board,myPosition,1,1);
        checkDiagonal(board,myPosition,-1,1);
        checkDiagonal(board,myPosition,1,-1);
        checkDiagonal(board,myPosition,-1,-1);
        return allPos;
    }
    public void checkDiagonal(ChessBoard b, ChessPosition start, int directionR, int directionC){
        ChessGame.TeamColor color = b.getPiece(start).getTeamColor();
        for (int r = start.getRow(), c = start.getColumn(); c < 9 && r < 9 && 0<r && 0<c; c+=directionC, r+=directionR) {

            if (null != b.getPiece(new ChessPosition(r, c))) {
                ChessPiece other = b.getPiece(new ChessPosition(r, c));
                if (other.getTeamColor() == color) {
                    c = 12;
                } else {
                    allPos.add(new ChessMove(start, new ChessPosition(r, c), null));
                    c = 12;
                }
            } else {
                allPos.add(new ChessMove(start, new ChessPosition(r, c), null));
            }


        }
    }
}