package com.example.javafx_train.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class GetTheConnection {
    static Connection connection=null;
    public static Connection getConnect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/billing", "root", "root");
        return connection;
    }

}