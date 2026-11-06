package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MySQLUserDAO implements UserDAO{
    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void createUser(UserData userdata) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public HashMap<String, UserData> getMemory() {
        return null;
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS user (
              `username` varchar(256) COLLATE utf8mb4_0900_as_cs NOT NULL,
              `email` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              PRIMARY KEY (`username`),
              UNIQUE (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
