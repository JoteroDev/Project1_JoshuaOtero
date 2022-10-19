package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException var3) {
            System.out.println("CLASS WASN'T FOUND");
            var3.printStackTrace();
        }

        String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=projectone";
        String username = "postgres";
        String password = "mkolmkol4";
        return DriverManager.getConnection(url, username, password);
    }


}
