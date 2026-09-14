package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor color;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    /// Help with debugging this won't work need to toString the ENUMs
    public String toString() {
        String s="error";
        if (this.type==PieceType.BISHOP){
            s="b";
        }
        else if (this.type==PieceType.ROOK){
            s="r";
        }
        else if (this.type==PieceType.KING){
            s="k";
        }
        else if (this.type==PieceType.PAWN){
            s="p";
        }
        else if (this.type==PieceType.QUEEN){
            s="q";
        }
        if (this.color== ChessGame.TeamColor.WHITE){
            return s.toUpperCase();
        }
        else{
            return s;
        }

    }
    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (o==null||this.getClass()!=o.getClass()) return false;
        ChessPiece other=(ChessPiece) o;
        if (other.getTeamColor()==this.color&&other.getPieceType()==this.type){return true;}
        else{return false;}
    }
    @Override
    public int hashCode(){
        int temp=10;
        if(this.color==ChessGame.TeamColor.WHITE){
            if (this.type==PieceType.BISHOP){
                return 12*temp+3;
            }
            else if (this.type==PieceType.ROOK){
                return 11*temp+2;
            }
            else if (this.type==PieceType.KING){
                return 10*temp+4;
            }
            else if (this.type==PieceType.PAWN){
                return 9*temp+3;
            }
            else if (this.type==PieceType.QUEEN){
                return 8*temp+5;
            }

        }
        else{
            temp=12;
            if (this.type==PieceType.BISHOP){
                return 12*temp+3;
            }
            else if (this.type==PieceType.ROOK){
                return 11*temp+2;
            }
            else if (this.type==PieceType.KING){
                return 10*temp+4;
            }
            else if (this.type==PieceType.PAWN){
                return 9*temp+3;
            }
            else if (this.type==PieceType.QUEEN){
                return 8*temp+5;
            }
        }

        return temp;
    }
}

