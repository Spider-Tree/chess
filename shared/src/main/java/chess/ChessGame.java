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
    private ChessPosition[] kingLookup={new ChessPosition(1,5),new ChessPosition(8,5)};
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
                if (null!=currentBoard.getPiece(new ChessPosition(r,c))){
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

       //Prepare Variables
        TeamColor color=currentBoard.getPiece(startPosition).getTeamColor();
        ArrayList<ChessMove> allMoves=new ArrayList<>();
        ArrayList<ChessMove> vMoves=new ArrayList<>();
        allMoves.addAll(currentBoard.getPiece(startPosition).pieceMoves(currentBoard,startPosition));
        ChessBoard testBoard=currentBoard;
        ChessBoard currentGameState=currentBoard;

        //For each move check to see if it is valid
        for (ChessMove move: allMoves){
            testBoard=currentGameState;
            testBoard.makeMove(move);
            currentBoard=testBoard;
            if(!this.isInCheck(color)){
                vMoves.add(move);
            }

        }
        currentBoard=currentGameState;

        return vMoves;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");

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
                if (null!=currentBoard.getPiece(new ChessPosition(r,c))){
                 ChessPiece P=currentBoard.getPiece(new ChessPosition(r,c));
                 if (teamColor!=P.getTeamColor()){
                 total.addAll(P.pieceMoves(currentBoard,new ChessPosition(r,c)));
                 }
                }
            }
        }

        //System.out.println(this.getKingPosition(teamColor));
        for (ChessMove move:total){


            if(this.getKingPosition(teamColor).equals(move.getEndPosition())){
                //System.out.println("True");
                return true;
            }
        }


        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public Collection<ChessMove> getAllValid(TeamColor teamColor){
        ArrayList<ChessMove> totalValid=new ArrayList<>();
        for(int r=1;r<9;r++){
            for(int c=1;c<9;c++){
                if (null!=currentBoard.getPiece(new ChessPosition(r,c))) {
                    ChessPiece P = currentBoard.getPiece(new ChessPosition(r, c));
                    if (P.getTeamColor() == teamColor) {
                        totalValid.addAll(this.validMoves(new ChessPosition(r, c)));
                    }
                }
            }
        }
        return totalValid;
    }
    public boolean isInCheckmate(TeamColor teamColor) {
        //Check to see if the team has any valid moves.
        //If team validMoves is empty. && isinCheck is true return true
        ArrayList<ChessMove> valid=new ArrayList<>();
        valid.addAll(getAllValid(teamColor));

        //isEmpty might not work the way I want depending on how addAll works
        if(valid.isEmpty()&&isInCheck(teamColor)){
            return true;
        }
        else{
            return false;
        }
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
        for(int r=1;r<9;r++){
            for(int c=1;c<9;c++){
                if (null!=currentBoard.getPiece(new ChessPosition(r,c))){
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
        }

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
