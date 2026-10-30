package dataaccess;

import com.google.gson.Gson;

import java.util.Map;

public class DataAccessException extends Exception {
    public enum Code{
        SERVER_ERROR,
        CLIENT_ERROR,
        ALREADY_TAKEN_ERROR,
        UNAUTHORIZED_ERROR



    }
    final private Code code;

    public DataAccessException(Code code, String message) {
        super(message);
        this.code=code;
    }
    public DataAccessException(Code code, String message, Throwable ex){
        super(message);
        this.code =code;
    }
    public String toJson(){
        return new Gson().toJson(Map.of("message", getMessage(), "status", code));
    }
    public static Code fromHttpStatusCode(int httpStatusCode) {
        return switch(httpStatusCode){
            case 500 -> Code.SERVER_ERROR;
            case 400 -> Code.CLIENT_ERROR;
            case 403 -> Code.ALREADY_TAKEN_ERROR;
            case 401 -> Code.UNAUTHORIZED_ERROR;
            default -> throw new IllegalArgumentException("Unknow HTTP status code: " + httpStatusCode);
        };
    }
    public int toHttpStatusCode(){
        return switch (code){
            case SERVER_ERROR -> 500;
            case CLIENT_ERROR -> 400;
            case ALREADY_TAKEN_ERROR -> 403;
            case UNAUTHORIZED_ERROR -> 401;
        };
    }
}
