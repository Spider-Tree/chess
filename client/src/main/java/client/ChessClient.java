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
    public void run() {
        System.out.println("Welcome to the chess game. Sign in to start");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
    }

    public String help(){
        if (state==State.SIGNEDOUT){
            return """
                    - register <USERNAME> <PASSWORD> <EMAIL> - to create an acccount
                    - login <USERNAME> <PASSWORD> - to play chess
                    - quit
                    - help - lists possible commands
                    """;
        }
        else{
            return """
                    - create <NAME> - create a game
                    - join <ID> [WHITE|BLACK] - join a game
                    - observe <ID> - watch a game
                    - logout
                    - quit
                    - help - lists possible commands
                    """;
        }

    }
}
