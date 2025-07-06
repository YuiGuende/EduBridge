/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.*;  // <-- Câu này đúng

/**
 *
 * @author LEGION
 */
public class DBContext {

    public static Connection getConnection() throws SQLWarning  {
        try {
            String serverName = "localhost";
            String dbName = "EduBridge_database";
            String portNumber = "1433";
            String USERNAME = "sa";
            String PASSWORD = "123456";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=EduBridge_database;encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLWarning("sdasd");
        }
    }

}
