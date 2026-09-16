package chess;

import java.util.ArrayList;
import java.util.Collection;

public class King {
    ArrayList<ChessMove> allpos=new ArrayList<>();
    public King(){}
    public Collection<ChessMove> pieceMoveKing(ChessBoard board, ChessPosition myPosition){
        int row=myPosition.getRow();
        int col=myPosition.getColumn();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();
        for (int c= -1; c<2;c++){
            if (0<c+col && c+col<9){
            for (int r= -1; r<2;r++){
                if (0<r+row && r+row<9){
                if (null==board.getPiece(new ChessPosition(row+r,col+c))){
                    allpos.add(new ChessMove(myPosition,new ChessPosition(row+r,col+c), null));
                }
                else if(color!=board.getPiece(new ChessPosition(row+r,col+c)).getTeamColor()){
                    allpos.add(new ChessMove(myPosition,new ChessPosition(row+r,col+c), null));
                }
                }
            }
            }
        }
        return allpos;
    }
}
