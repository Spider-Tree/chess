package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn {
    ArrayList<ChessMove> allPos=new ArrayList<>();
    public Pawn(){}
    public Collection<ChessMove> pieceMovePawn(ChessBoard board, ChessPosition myPosition){
       ChessGame.TeamColor color =board.getPiece(myPosition).getTeamColor();
        //check color
        if(color==ChessGame.TeamColor.WHITE){
            moveLogicWhite(board,myPosition);
        }
        else{
            moveLogicBlack(board,myPosition);
        }
        return allPos;
    }

    public void moveLogicWhite(ChessBoard b, ChessPosition start){
        int row=start.getRow();
        int col=start.getColumn();
        //first check in front, then check diagonals
        if (row==2){
            for(int x=1;x<3;x++){
                if(null==b.getPiece(new ChessPosition(row+x,col))){
                    allPos.add(new ChessMove(start,new ChessPosition(row+x,col),null));
                }
                else{
                    x=10;
                }
            }
        }
        else if(row==7){
            //defaults to Queen add input later
            if(null==b.getPiece(new ChessPosition(row+1,col))) {
                allPos.add(new ChessMove(start,new ChessPosition(row+1,col), ChessPiece.PieceType.QUEEN));
                allPos.add(new ChessMove(start,new ChessPosition(row+1,col), ChessPiece.PieceType.KNIGHT));
                allPos.add(new ChessMove(start,new ChessPosition(row+1,col), ChessPiece.PieceType.BISHOP));
                allPos.add(new ChessMove(start,new ChessPosition(row+1,col), ChessPiece.PieceType.ROOK));
            }
        }
        else{
            if(null==b.getPiece(new ChessPosition(row+1,col))){
                allPos.add(new ChessMove(start,new ChessPosition(row+1,col),null));
            }
        }

        if (row == 7) {
            for (int c= -1;c<2;c+=2){
                //System.out.println(c);
            if (0<col+c && col+c<9) {
                if (null != b.getPiece(new ChessPosition(row + 1, col + c))) {
                    System.out.println(new ChessPosition(row+1,col+c));
                    if (ChessGame.TeamColor.BLACK == b.getPiece(new ChessPosition(row + 1, col + c)).getTeamColor()) {
                       // System.out.println(col);
                        //System.out.println(c);
                        //System.out.println(col+c);
                        allPos.add(new ChessMove(start, new ChessPosition(row + 1, col + c), ChessPiece.PieceType.QUEEN));
                        allPos.add(new ChessMove(start, new ChessPosition(row + 1, col + c), ChessPiece.PieceType.KNIGHT));
                        allPos.add(new ChessMove(start, new ChessPosition(row + 1, col + c), ChessPiece.PieceType.BISHOP));
                        allPos.add(new ChessMove(start, new ChessPosition(row + 1, col + c), ChessPiece.PieceType.ROOK));
                    }
                }
            }
            }
        }
        else{
            for(int c= -1; c<2; c+=2){
                if(0<col+c && col+c<9){
                    if(null != b.getPiece(new ChessPosition(row+1,col+c))){
                        if(ChessGame.TeamColor.BLACK==b.getPiece(new ChessPosition(row+1,col+c)).getTeamColor()){
                            allPos.add(new ChessMove(start, new ChessPosition(row+1,col+c),null));
                        }
                    }
                }
            }
        }


    }
    public void moveLogicBlack(ChessBoard b, ChessPosition start){
        int row=start.getRow();
        int col=start.getColumn();
        //first check in front, then check diagonals
        if (row==7){
            for(int x=1;x<3;x++){
                if(null==b.getPiece(new ChessPosition(row-x,col))){
                    allPos.add(new ChessMove(start,new ChessPosition(row-x,col),null));
                }
                else{
                    x=10;
                }
            }
        }
        else if(row==2){
            //defaults to Queen add input later
            if(null==b.getPiece(new ChessPosition(row-1,col))) {
                allPos.add(new ChessMove(start,new ChessPosition(row-1,col), ChessPiece.PieceType.QUEEN));
                allPos.add(new ChessMove(start,new ChessPosition(row-1,col), ChessPiece.PieceType.KNIGHT));
                allPos.add(new ChessMove(start,new ChessPosition(row-1,col), ChessPiece.PieceType.BISHOP));
                allPos.add(new ChessMove(start,new ChessPosition(row-1,col), ChessPiece.PieceType.ROOK));
            }
        }
        else{
            if(null==b.getPiece(new ChessPosition(row-1,col))){
                allPos.add(new ChessMove(start,new ChessPosition(row-1,col),null));
            }
        }

        if (row == 2) {
            for (int c= -1;c<2;c+=2){
                if (0<col+c && col+c<9) {
                    if (null != b.getPiece(new ChessPosition(row - 1, col + c))) {

                        if (ChessGame.TeamColor.WHITE == b.getPiece(new ChessPosition(row - 1, col + c)).getTeamColor()) {
                            allPos.add(new ChessMove(start,new ChessPosition(row-1,col+c), ChessPiece.PieceType.QUEEN));
                            allPos.add(new ChessMove(start,new ChessPosition(row-1,col+c), ChessPiece.PieceType.KNIGHT));
                            allPos.add(new ChessMove(start,new ChessPosition(row-1,col+c), ChessPiece.PieceType.BISHOP));
                            allPos.add(new ChessMove(start,new ChessPosition(row-1,col+c), ChessPiece.PieceType.ROOK));

                        }
                    }
                }
            }
        }
        else{
            for(int c= -1; c<2; c+=2){
                if(0<col+c && col+c<9){
                    if(null != b.getPiece(new ChessPosition(row-1,col+c))){
                        if(ChessGame.TeamColor.WHITE==b.getPiece(new ChessPosition(row-1,col+c)).getTeamColor()){
                            allPos.add(new ChessMove(start, new ChessPosition(row-1,col+c),null));
                        }
                    }
                }
            }
        }
    }
}
