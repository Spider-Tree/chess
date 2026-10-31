package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import service.*;
import model.*;

import java.util.Collection;

public class Server {

    private final Javalin javalin;
    private final UserService userService;
    private final GameService gameService;
    private final AuthService authService;
    private final Gson serializer= new Gson();
    public Server() {
        userService=new UserService(new MemoryUserDAO());
        gameService=new GameService(new MemoryGameDAO());
        authService=new AuthService(new MemoryAuthDAO());

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                //.post("/user", this::addUser)
                .delete("/db",this::clearAll)
                .post("/user",this::addUser)
                .post("/session",this::loginUser)
                .delete("/session",this::logoutUser)
                .get("/game",this::listGames)
                .post("/game",this::createGame)
                .put("/game",this::joinGame)
                .exception(DataAccessException.class,this::errorHandler)



        ;

        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
    private void clearAll(Context ctx) throws DataAccessException{
        authService.clear();
        userService.clear();
        gameService.clear();
        ctx.status(200);

    }
    private void addUser(Context ctx) throws DataAccessException {
        UserData user = serializer.fromJson(ctx.body(),UserData.class);
        if (user==null||user.username()==null||user.password()==null||user.email()==null){
            throw new DataAccessException(DataAccessException.Code.CLIENT_ERROR,"Error: Missing required data for registration");
        }
        user = userService.addUser(user);
        AuthData auth=authService.createAuth(user.username());
        RegisterResult result=new RegisterResult(user.username(), auth.authToken());
        var jsonResult = serializer.toJson(result);
        ctx.json(jsonResult);
    }
    private void errorHandler(DataAccessException ex, Context ctx){
        ctx.status(ex.toHttpStatusCode());
        ctx.json(ex.toJson());
    }
    private void loginUser(Context ctx) throws DataAccessException {
        LoginRequest request = serializer.fromJson(ctx.body(),LoginRequest.class);
        if(request==null||request.password()==null||request.username()==null){
            throw new DataAccessException(DataAccessException.Code.CLIENT_ERROR,"Error: Missing password or username  for login");
        }

        UserData user =userService.loginUser(request);
        AuthData auth = authService.createAuth(user.username());
        LoginResult result=new LoginResult(auth.username(), auth.authToken());
        var jsonResult = serializer.toJson(result);
        ctx.json(jsonResult);
    }

    private void logoutUser(Context ctx) throws DataAccessException {
      String authToken = ctx.header("Authorization");
  if(authToken==null||authToken.isBlank()){
      throw new DataAccessException(DataAccessException.Code.UNAUTHORIZED_ERROR,"Error: Invalid authToken");
  }
        //System.out.println(authToken);
        authService.logout(authToken);
        ctx.status(200);
    }

    private void listGames(Context ctx) throws DataAccessException{
        String authToken=ctx.header("Authorization");
        if(authToken==null||authToken.isBlank()||authService.getAuthData(authToken)==null){
            throw new DataAccessException(DataAccessException.Code.UNAUTHORIZED_ERROR,"Error: unauthorized");
        }
        Collection<GameData> games = gameService.listGames();
        ListGamesResult result = new ListGamesResult(games);
        var jsonResult=serializer.toJson(result);
        ctx.json(jsonResult);
    }
    private void createGame(Context ctx) throws DataAccessException {
        String authToken=ctx.header("Authorization");
        if(authToken==null||authToken.isBlank()||authService.getAuthData(authToken)==null){
            throw new DataAccessException(DataAccessException.Code.UNAUTHORIZED_ERROR,"Error: unauthorized");
        }
        CreateGameRequest request=serializer.fromJson(ctx.body(),CreateGameRequest.class);
        if (request.gameName()==null||request.gameName().isBlank()){
            throw new DataAccessException(DataAccessException.Code.CLIENT_ERROR,"Error: invalid game name");
        }
        GameData game =gameService.createGame(request.gameName());
        CreateGameResult result = new CreateGameResult(game.gameID());
        var jsonResult=serializer.toJson(result);
        ctx.json(jsonResult);

    }
    private void joinGame(Context ctx) throws DataAccessException{
        String authToken=ctx.header("Authorization");
        AuthData auth=authService.getAuthData(authToken);
        if(authToken==null||authToken.isBlank()||auth==null){
            throw new DataAccessException(DataAccessException.Code.UNAUTHORIZED_ERROR,"Error: unauthorized");
        }
        JoinGameData data=serializer.fromJson(ctx.body(),JoinGameData.class);
        if(gameService.getGame(data.gameID())==null){
            throw new DataAccessException(DataAccessException.Code.CLIENT_ERROR,"Error: bad request");
        }
        ChessGame.TeamColor color;
        if(data.playerColor()==null){
            throw new DataAccessException(DataAccessException.Code.CLIENT_ERROR,"Error: bad request");
        }
        if(data.playerColor().equals("WHITE")){
             color= ChessGame.TeamColor.WHITE;
        } else if (data.playerColor().equals("BLACK")) {
            color= ChessGame.TeamColor.BLACK;
        }
        else{
            throw new DataAccessException(DataAccessException.Code.CLIENT_ERROR,"Error: bad request");
        }

        String username=auth.username();
        JoinGameRequest request=new JoinGameRequest(color,data.gameID(),username);
        gameService.joinGame(request);
        ctx.status(200);

    }
}
