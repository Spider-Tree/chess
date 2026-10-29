package server;

import com.google.gson.Gson;
import dataaccess.*;
import dataaccess.DataAccessException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Map;
import service.*;
public class Server {

    private final Javalin javalin;
    private final UserService userService;
    private final GameService gameService;
    private final AuthService authService;

    public Server(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        userService=new UserService(userDAO);
        gameService=new GameService(gameDAO);
        authService=new AuthService(authDAO);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                //.post("/user", this::addUser)
                .delete("/db",this::clearAll)

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
        ctx.status(204);
    }


}
