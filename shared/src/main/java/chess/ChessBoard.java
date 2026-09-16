package chess;
import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] board;
    public ChessBoard() {
        board=new ChessPiece[8][8];
    }
    public ChessBoard(ChessPiece[][] board){
        this.board=board;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()-1][position.getColumn()-1]=piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {

        if (board[position.getRow()-1][position.getColumn()-1]==null){
            return null;
        }
        else{
            return board[position.getRow()-1][position.getColumn()-1];
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //Pawns->Rooks->Knights->Bishops->Queens->Kings
        for (int c=1;c<9;c++){
            this.addPiece(new ChessPosition(2,c),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
            this.addPiece(new ChessPosition(7,c),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }
        for (int c=1;c<9;c+=7){
            this.addPiece(new ChessPosition(1,c),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
            this.addPiece(new ChessPosition(8,c),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        }
        for (int c=2;c<9;c+=5){
            this.addPiece(new ChessPosition(1,c),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
            this.addPiece(new ChessPosition(8,c),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        }
        for (int c=3;c<8;c+=3){
            this.addPiece(new ChessPosition(1,c),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
            this.addPiece(new ChessPosition(8,c),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        }

        this.addPiece(new ChessPosition(1,4),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
        this.addPiece(new ChessPosition(8,4),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));

        this.addPiece(new ChessPosition(1,5),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
        this.addPiece(new ChessPosition(8,5),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));

    }
    /**
     *  Loops through each space and generates a visual representation of the chess board. Might be in CHessGame
     */
    public void displayBoard(){
       for (int r=0; r<this.board.length;r++){
           for (int c=0;c<this.board[r].length;c++){
               ChessPosition P= new ChessPosition(r,c);
               System.out.println(this.getPiece(P));
           }
       }
    }


    @Override
    public boolean equals(Object o){
        if (this==o){return true;}
        if (o==null||this.getClass()!=o.getClass()){return false;}
        ChessBoard other =(ChessBoard) o;
        if (Arrays.deepEquals(this.board,other.board)){return true;}
        return false;
    }

    @Override
    public int hashCode(){
        int total=0;
        for (int r=1;r<(this.board.length)+1;r++){
            for (int c=1;c<(this.board[r-1].length+1);c++){

                ChessPosition Pos=new ChessPosition(r,c);
                if (this.getPiece(Pos)!=null){
                total += this.getPiece(Pos).hashCode();}
                else{total+=r+c;}
            }
        }
        return total;
    }
    @Override
    public String toString(){
        String tempTotal="";
        for(int r=1;r<this.board.length+1;r++){
            for (int c=1;c<(this.board[r-1].length+1);c++){

                 tempTotal+=this.getPiece(new ChessPosition(r,c)).toString();
            }
        }
        return tempTotal;

    }
}
