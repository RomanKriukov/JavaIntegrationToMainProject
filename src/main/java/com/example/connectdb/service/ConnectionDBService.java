package com.example.connectdb.service;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class ConnectionDBService {

    public Connection connectToDB(String username, String password){
        Connection connection = null;
        String urlToDB = "jdbc:sqlserver://prometey.net.ua;database=fa;encrypt=true;trustServerCertificate=true";

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(urlToDB, username, password);

            if (connection != null){
                System.out.println("Підключення до бази даних успішне!");
            }
            else {
                System.out.println("Не вдалося підключитися до бази даних!");
            }
        }catch (ClassNotFoundException e){
            System.out.println("Помилка завантаження драйвера: " + e.getMessage());
        }catch (SQLException e){
            System.out.println("Помилка підключення до бази даних: " + e.getMessage());
        }

        return connection;
    }

    public void closeConnection(Connection connection){
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Підключення до бази даних закрито.");
            } catch (SQLException e) {
                System.err.println("Помилка закриття підключення: " + e.getMessage());
            }
        }
    }

    public ResultSet query(String sql, Connection connection){
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return resultSet;
    }
}
