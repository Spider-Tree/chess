package exception;


import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ResponseException extends Exception {

    public enum Code {
        SERVER_ERROR,
        CLIENT_ERROR,
        ALREADY_TAKEN_ERROR,
        UNAUTHORIZED_ERROR
    }

    private final Code code;

    public ResponseException(Code code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseException(Code code, String message, Throwable ex) {
        super(message);
        this.code = code;
    }

    public String toJson() {
        return new Gson().toJson(Map.of("message", getMessage(), "status", code));
    }

    public static ResponseException fromJson(String json) {
        var map = new Gson().fromJson(json, HashMap.class);
        var status = Code.valueOf(map.get("status").toString());
        String message = map.get("message").toString();
        return new ResponseException(status, message);
    }
    public int toHttpStatusCode() {
        return switch (code) {
            case SERVER_ERROR -> 500;
            case CLIENT_ERROR -> 400;
            case ALREADY_TAKEN_ERROR -> 403;
            case UNAUTHORIZED_ERROR -> 401;
        };
    }
    public static Code fromHttpStatusCode(int httpStatusCode){
        return switch (httpStatusCode){
            case 500 -> Code.SERVER_ERROR;
            case 400 -> Code.CLIENT_ERROR;
            case 403 -> Code.ALREADY_TAKEN_ERROR;
            case 401 -> Code.UNAUTHORIZED_ERROR;
            default -> throw new IllegalArgumentException("UNknow Http status code");
        };
    }


    public Code getCode() {
        return code;
    }
}
