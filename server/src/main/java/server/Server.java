package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import service.*;
import model.*;

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


}
