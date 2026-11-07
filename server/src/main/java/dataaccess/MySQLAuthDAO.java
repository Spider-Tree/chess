package dataaccess;

import com.google.gson.Gson;
import model.AuthData;

import java.util.HashMap;


import java.sql.*;

import static java.sql.Types.NULL;

public class MySQLAuthDAO implements AuthDAO {

    public MySQLAuthDAO() throws DataAccessException {
        configureAuthDatabase();
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auth";
        executeUpdate(statement);
    }

    @Override
    public void createAuth(AuthData authdata) throws DataAccessException {
        var statement = "INSERT INTO auth (authToken, authData) VALUES (?, ?)";
        String json = new Gson().toJson(authdata);
        executeUpdate(statement, authdata.authToken(), json);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, authData FROM auth WHERE authToken=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(DataAccessException.Code.SERVER_ERROR, "Error: Unable to connect");
        }
        return null;
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var json = rs.getString("authData");
        AuthData authData = new Gson().fromJson(json, AuthData.class);
        return authData;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authToken=?";
        executeUpdate(statement, authToken);
    }

    @Override
    public HashMap<String, AuthData> getMemory() {
        return null;
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS auth (
              `authToken` varchar(256) COLLATE utf8mb4_0900_as_cs NOT NULL,
              `authData` JSON NOT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

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
            throw new DataAccessException(DataAccessException.Code.SERVER_ERROR, "Error: Unable to update database");
        }
    }

    private void configureAuthDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(DataAccessException.Code.SERVER_ERROR, "Error: Unable to configure database");
        }
    }
}
