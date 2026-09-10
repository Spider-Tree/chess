package chess;
/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private ChessPosition start;
    private ChessPosition end;
    private ChessPiece.PieceType promotion;
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.start=startPosition;
        this.end=endPosition;
        this.promotion=promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return start;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return end;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotion;
    }
    @Override
    public boolean equals(Object obj){
        if (this==obj){
            return true;
        }
        if (!(obj instanceof ChessMove other)){
            return false;
        }

        return start.equals(other.start)&& end.equals(other.end)&&promotion==other.promotion;
    }
    @Override
    public int hashCode(){
        int temp=10;
    switch (promotion) {
        case BISHOP:
            temp=1;
            break;
        case KNIGHT:
            temp=2;
            break;
        case KING:
            System.out.println("Error can't promote to king");
            break;
        case QUEEN:
            temp=3;
            break;
        case ROOK:
            temp=4;
            break;
        case null:
            temp=0;
            break;
        case PAWN:
            System.out.println("Error: can't promote to pawn");
            break;
    }
        return 47 * (3*end.getRow()+10*end.getColumn()+2*start.getRow()+start.getColumn()+temp);
    }

}
