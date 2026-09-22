package chess;

import java.util.Collection;
import java.util.ArrayList;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private int turnCounter=1;
    private boolean teamTurn=true;
    private ChessBoard currentBoard;
    // 0 index will be White King,1 index will be Black King
    private ChessPosition[] kingLookup={null,null};
    public ChessGame() {
        this.currentBoard=new ChessBoard();
        currentBoard.resetBoard();
        kingLookup[0]=new ChessPosition(1,5);
        kingLookup[1]= new ChessPosition(8,5);
    }
    public ChessGame(ChessBoard board, boolean teamTurn){
        this.teamTurn=teamTurn;
        this.currentBoard=board;
        for(int r=1;r<9;r++){
            for(int c=1;c<9;c++){
                ChessPiece P=currentBoard.getPiece(new ChessPosition(r,c));
                if(P.getTeamColor()==TeamColor.WHITE && P.getPieceType()==ChessPiece.PieceType.KING){
                    ChessPosition Pos=new ChessPosition(r,c);
                    kingLookup[0]=Pos;
                }
                else if(P.getPieceType()== ChessPiece.PieceType.KING){
                    ChessPosition Pos=new ChessPosition(r,c);
                    kingLookup[1]=Pos;
                }
            }
        }
        //If there is not two kings return invalid board error

    }
    public ChessGame(ChessBoard board, boolean teamTurn, ChessPosition[] lookup){
        this.teamTurn=teamTurn;
        this.currentBoard=board;
        this.kingLookup=lookup;

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        if (teamTurn){
            return TeamColor.WHITE;
        }
        else{
            return TeamColor.BLACK;
        }

    }


    public ChessPosition getKingPosition(TeamColor color){
        if (color==TeamColor.WHITE){
            return kingLookup[0];
        }
        else{
            return kingLookup[1];
        }
    }

    public void setKingPosition(TeamColor color,ChessPosition pos){
        if (color==TeamColor.WHITE){
            kingLookup[0]=pos;
        }
        else{
            kingLookup[1]=pos;
        }
    }
    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        if (team==TeamColor.WHITE){
            teamTurn=true;
        }
        else if (team==TeamColor.BLACK){
            teamTurn=false;
        }
        else{
            System.out.println("Something broke");
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //Given Postion->getCollection of all moves
        //create a function that makes move
        //make temporary new board if the new move made
        //if temp board isIncheck=false add move to validMoveList
        //
        //
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
        //addPiece  null which removes.
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        //code that goes through each position and gets all moves each position on the opposing team could make
        //if position is null or own team color ignore it.
        //create a collection of all their moves
        //see if any moves have an END position that overlaps with Kings current Position.
        ArrayList<ChessMove> total=new ArrayList<>();
        for (int r=1;r<9;r++){
            for (int c=1;c<9;c++){
                 ChessPiece P=currentBoard.getPiece(new ChessPosition(r,c));
                 if (teamColor!=P.getTeamColor()){
                 total.addAll(P.pieceMoves(currentBoard,new ChessPosition(r,c)));
                 }
            }
        }
        for (ChessMove move:total){
            if(this.getKingPosition(teamColor)==move.getEndPosition()){
                return true;
            }
        }
        //also should go back and see if I can change ChessPiece to has all move logic.

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //Check to see if the team has any valid moves.
        //If team validMoves is empty. && isinCheck is true return true

        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // if validMove empty && not in check is stalement
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.currentBoard=board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentBoard;
    }

    @Override
    public int hashCode(){
        return 1;
    }
    @Override
    public String toString(){
        return "Game Turn: "+turnCounter;
    }
    @Override
    public boolean equals(Object obj){
        return false;
    }
}
