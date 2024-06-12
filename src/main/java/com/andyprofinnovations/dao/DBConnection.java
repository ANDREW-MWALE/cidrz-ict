package com.andyprofinnovations.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConn() {
//        String loadDriver = "com.mysql.cj.jdbc.Driver"; // MySQL driver
//        String hostname = "localhost"; // MySQL server hostname
//        String port = "3306"; // MySQL server port (default is 3306)
//        String databaseName = "buyzambiacommerce"; // Name of your MySQL database
//        String username = "root"; // MySQL username
//        String password = ""; // MySQL password
//
//        String connectionUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + databaseName +
//                "?user=" + username +
//                "&password=" + password +
//                "&useSSL=false"; // Disable SSL for simplicity
//
//        Connection con = null;
//
//        try {
//            Class.forName(loadDriver);
//            con = DriverManager.getConnection(connectionUrl);
//        } catch (ClassNotFoundException e) {
//            System.err.println("Failed to load JDBC driver: " + loadDriver);
//            e.printStackTrace();
//        } catch (SQLException e) {
//            System.err.println("Failed to establish database connection");
//            e.printStackTrace();
//        }
//
//        return con;
//    }
        // public static Connection getConn() {
        String loadDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String hostname = "DESKTOP-O2OGT7Q";
        String instanceName = "\\SQLEXPRESS";
        String databaseName = "cidrz_ict";
        String username = "sa";
        String password = "12345";

        String connectionUrl = "jdbc:sqlserver://" + hostname + instanceName +
                ";databaseName=" + databaseName +
                ";user=" + username +
                ";password=" + password +
                ";integratedSecurity=false;encrypt=false;trustServerCertificate=false;";

        Connection con = null;

        try {
            Class.forName(loadDriver);
            con = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load JDBC driver: " + loadDriver);
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection");
            e.printStackTrace();
        }

        return con;
    }
}
