package chess;

import java.util.Arrays;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private final int row;
    private final int col;
    public ChessPosition(int row, int col) {
        this.row=row;
        this.col=col;
    }
    public ChessPosition(ChessPosition pos){
        this.row=pos.getRow();
        this.col=pos.getColumn();
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left column
     */
    public int getColumn() {
        return col;
    }
    /**
     * Changes default object equals to work with the chessPositionTests; includes Hashcode
    **/
    @Override
    public boolean equals(Object o){
        if (this==o){return true;}
        if (o==null||this.getClass()!=o.getClass()){return false;}
        ChessPosition other =(ChessPosition) o;
        if (this.getColumn()== other.getColumn() && this.getRow()==other.getRow()){
            return true;
        }
        else{return false;}

    }
    @Override
    public int hashCode(){

        return 31* row+col;
    }
    @Override
    public String toString(){
        //(row, col)
        return ("("+row+", "+col+")");
    }
}
