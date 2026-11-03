package dataaccess;

import model.AuthData;

import java.util.HashMap;
import model.*;
import java.sql.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLAuthDAO implements AuthDAO{

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void createAuth(AuthData authdata) throws DataAccessException {

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public HashMap<String, AuthData> getMemory() {
        return null;
    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS authToken (
              `username` varchar(256) COLLATE utf8mb4_0900_as_cs NOT NULL,
              `authToken` varchar(256) NOT NULL,
              PRIMARY KEY (`username`),
              UNIQUE (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
    private void configureAuthDatabase() throws DataAccessException{
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()){
            for (String statement : createStatements){
                try (var preparedStatement = conn.prepareStatement(statement)){
                    preparedStatement.executeUpdate();
                }
            }
        }catch (SQLException ex){
            throw new DataAccessException(DataAccessException.Code.SERVER_ERROR,"Unable to configure database");
        }
    }
}
