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
        System.out.println(SET_TEXT_COLOR_BLACK+"Welcome to the chess game. Sign in to start");
        System.out.print(SET_TEXT_COLOR_DARK_GREY+help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")){
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE+result);
            } catch (Throwable ex){
                var msg = ex.toString();
                System.out.print(msg);
            }
        }
    }
    private void printPrompt(){
        System.out.print("\n"+ RESET_TEXT_COLOR+">>> "+SET_TEXT_COLOR_GREEN);
    }

    public String eval(String input){
        try{
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length>0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd){
                case "register" -> register(params);
                case "login" -> logIn(params);
                case "create" -> createGame(params);
                case "join" -> joinGame(params);
                case "logout" -> logOut();
                case "observe" -> observeGame(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex){
            return ex.getMessage();
        }
    }

    public String logIn(String... params) throws ResponseException{
        return null;
    }
    public String register(String... params) throws ResponseException{
        return null;
    }
    public String createGame(String... params) throws ResponseException{
        return null;
    }
    public String joinGame(String... params) throws ResponseException{
        return null;
    }
    public String logOut() throws ResponseException{
        return null;
    }
    public String observeGame(String... params) throws ResponseException{
        return null;
    }

    public String help(){
        if (state==State.SIGNEDOUT){
            return """
                    - register <USERNAME> <PASSWORD> <EMAIL> - to create an account
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
