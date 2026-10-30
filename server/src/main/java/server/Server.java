package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccess.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Map;
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
    public void clearAll(Context ctx) throws DataAccessException{
        authService.clear();
        userService.clear();
        gameService.clear();
        ctx.status(200);
    }
    public void addUser(Context ctx) throws AlreadyTakenException, DataAccessException {
        UserData user = serializer.fromJson(ctx.body(),UserData.class);

        user = userService.addUser(user);
        AuthData auth=authService.createAuth(user.username());
        RegisterResult result=new RegisterResult(user.username(), auth.authToken());
        var jsonResult = serializer.toJson(result);
        ctx.json(jsonResult);
    }


}
