package dataaccess;

import com.google.gson.Gson;

import java.util.Map;

public class DataAccessException extends Exception {

    public enum Code {
        SERVER_ERROR,
        CLIENT_ERROR,
        ALREADY_TAKEN_ERROR,
        UNAUTHORIZED_ERROR
    }

    private final Code code;

    public DataAccessException(Code code, String message) {
        super(message);
        this.code = code;
    }

    public DataAccessException(Code code, String message, Throwable ex) {
        super(message);
        this.code = code;
    }

    public String toJson() {
        return new Gson().toJson(Map.of("message", getMessage(), "status", code));
    }

    public int toHttpStatusCode() {
        return switch (code) {
            case SERVER_ERROR -> 500;
            case CLIENT_ERROR -> 400;
            case ALREADY_TAKEN_ERROR -> 403;
            case UNAUTHORIZED_ERROR -> 401;
        };
    }

    public Code getCode() {
        return code;
    }
}
