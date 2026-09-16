package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Rook {
    ArrayList<ChessMove> allPos=new ArrayList<>();
    public Rook(){}
    public Collection<ChessMove> pieceMoveRook(ChessBoard board, ChessPosition myPosition){
        checkCol(board,myPosition,1);
        checkCol(board,myPosition,-1);
        checkRow(board,myPosition,1);
        checkRow(board,myPosition,-1);
        return allPos;
    }
    public void checkCol(ChessBoard b, ChessPosition start, int direction){
        ChessGame.TeamColor color=b.getPiece(start).getTeamColor();
        int col=start.getColumn();
        for (int r= start.getRow()+direction; r<9 &&0<r;r+=direction){
            if (null==b.getPiece(new ChessPosition(r,col))){
                allPos.add(new ChessMove(start,new ChessPosition(r,col),null));
            }
            else if(color!=b.getPiece(new ChessPosition(r,col)).getTeamColor()){
                allPos.add(new ChessMove(start,new ChessPosition(r,col),null));
                r=12;
            }
            else{
                r=12;
            }
        }
    }
    public void checkRow(ChessBoard b, ChessPosition start, int direction){
        ChessGame.TeamColor color=b.getPiece(start).getTeamColor();
        int row=start.getRow();
        for (int c= start.getColumn()+direction; c<9 &&0<c;c+=direction){
            if (null==b.getPiece(new ChessPosition(row,c))){
                allPos.add(new ChessMove(start,new ChessPosition(row,c),null));
            }
            else if(color!=b.getPiece(new ChessPosition(row,c)).getTeamColor()){
                allPos.add(new ChessMove(start,new ChessPosition(row,c),null));
                c=12;
            }
            else{
                c=12;
            }
        }
    }
}
