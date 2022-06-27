package ru.netology.web.data;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DbInteraction {
    private Connection getNewConnection() throws SQLException {
        String url = "jdbc:postgresql://192.168.99.100:5432/app";
        String user = "app";
        String passwd = "pass";
        return DriverManager.getConnection(url, user, passwd);
    }

    @Test
    public void shouldGetJdbcConnection() throws SQLException {
        try(Connection connection = getNewConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }

}
