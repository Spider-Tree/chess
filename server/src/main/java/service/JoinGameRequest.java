package service;

import chess.ChessGame;

public record JoinGameRequest(ChessGame.TeamColor color, int gameID,String username) {
}
