package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Knight {
    ArrayList<ChessMove> allpos;
    public Knight(){}
    public Collection<ChessMove> pieceMoveKnight(ChessBoard board, ChessPosition myPosition){

        // check the different directions
        checkL(board,myPosition,1,1);
        checkL(board,myPosition,-1,1);
        checkL(board,myPosition,1,-1);
        checkL(board,myPosition,-1,-1);
        return allpos;

    }
    public void checkL(ChessBoard b,ChessPosition start, int directionVert, int directionHori){
        int col=start.getColumn();
        int row= start.getRow();
        ChessGame.TeamColor color=b.getPiece(start).getTeamColor();
        ChessPosition pos1= new ChessPosition((2*directionVert)+row,(directionHori)+row);
        ChessPosition pos2= new ChessPosition(directionVert+row,(2*directionHori)+col);
        if (pos1.getRow()<9&&pos1.getColumn()<9&&0<pos1.getRow()&&0<pos1.getColumn()){
        if (null==b.getPiece(pos1)){
            allpos.add(new ChessMove(start,pos1,null));}
        else if(color!=b.getPiece(pos1).getTeamColor()){
            allpos.add(new ChessMove(start,pos1,null));
        }
        }
        if (pos2.getRow()<9&&pos2.getColumn()<9&&0<pos2.getRow()&&0<pos2.getColumn()) {
            if (null == b.getPiece(pos2)) {
                allpos.add(new ChessMove(start, pos1, null));
            } else if (color != b.getPiece(pos1).getTeamColor()) {
                allpos.add(new ChessMove(start, pos2, null));
            }
        }
    }
}
