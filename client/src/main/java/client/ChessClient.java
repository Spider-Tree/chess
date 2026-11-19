package client;
import java.util.Scanner;
import java.util.Arrays;

import com.google.gson.Gson;

import model.*;
import server.ServerFacade;

import static ui.EscapeSequences.*;
public class ChessClient {
    private String username=null;
    private final ServerFacade server;
    private State state = State.SIGNEDOUT;
    public ChessClient(String serverURL) throws ResponseException{
        server = new ServerFacade(serverURL);

    }
}
