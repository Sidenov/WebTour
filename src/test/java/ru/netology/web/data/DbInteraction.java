package ru.netology.web.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;


public class DbInteraction {

    private static String url = System.getProperty("db.url");
    private static String user = "app";
    private static String password = "pass";

    @BeforeEach
    @SneakyThrows
    private static Connection getConnection() {
         return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static String getStatusBuyCard() {
        var runner = new QueryRunner();
        var cardsSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";

        try (var conn = getConnection()) {
            String first = runner.query(conn, cardsSQL, new ScalarHandler<>());
            return first;
        }
    }
    @SneakyThrows
    public static String getStatusBuyCredit() {
        var runner = new QueryRunner();
        var cardsSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";

        try (var conn = getConnection()) {
            return runner.query(conn, cardsSQL, new ScalarHandler<>());
        }
    }

}
