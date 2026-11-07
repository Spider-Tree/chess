package dataaccess;

import com.google.gson.Gson;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.Types.NULL;

public class MySQLUserDAO implements UserDAO{
    public MySQLUserDAO() throws DataAccessException{
        configureUserDatabase();
    }
    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE user";
        executeUpdate(statement);
    }

    @Override
    public void createUser(UserData userdata) throws DataAccessException {
        var statement = "INSERT INTO user (username, userData) VALUES (?, ?)";
        String json = new Gson().toJson(userdata);
        executeUpdate(statement, userdata.username(), json);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            var statement = "SELECT username, userData FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1,username);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){
                        return readUser(rs);
                    }
                }
            }
            }  catch (SQLException e){
            throw new DataAccessException(DataAccessException.Code.SERVER_ERROR, "Unable to connect");
        }
        return null;
    }

    private UserData readUser(ResultSet rs) throws SQLException {
        var json = rs.getString("userData");
        UserData userData = new Gson().fromJson(json, UserData.class);
        return userData;
    }

    @Override
    public HashMap<String, UserData> getMemory() {
        return null;
    }

    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) {
                        ps.setString(i + 1, p);
                    } else if (param instanceof Integer p) {
                        ps.setInt(i + 1, p);
                    } else if (param == null) {
                        ps.setNull(i + 1, NULL);
                    }
                }
                ps.executeUpdate();


            }
        } catch (SQLException e) {
            throw new DataAccessException(DataAccessException.Code.SERVER_ERROR, "Unable to update database");
        }
    }
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS user (
              `username` varchar(256) COLLATE utf8mb4_0900_as_cs NOT NULL,
              `userData` JSON NOT NULL,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureUserDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(DataAccessException.Code.SERVER_ERROR, "Unable to configure database");
        }
    }
}
