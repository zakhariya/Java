package ua.lpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class AbstractDao {
    private String url;
    private String user;
    private String password;

    AbstractDao(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    abstract void executeSql(String sql) throws SQLException;

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
