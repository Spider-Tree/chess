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
