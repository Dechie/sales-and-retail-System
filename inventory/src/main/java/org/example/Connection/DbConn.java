package org.example.Connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConn {
    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {


            try {
                conn = DriverManager.getConnection(
                        "jdbc:mariadb://localhost:3306/zara_store",
                        "myuser",
                        "dechasa1234");
                if (conn == null)
                    System.out.println("could not establish database connection");
            } catch (Exception e) {
                e.getMessage();
            }

            System.out.println(conn == null);
        return conn;
    }


}

