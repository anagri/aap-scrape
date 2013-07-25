package com.creativei.aap;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.String.format;

public class MySQL implements Serializable {
    private Connection connection;
    private PreparedStatement insertStatement;

    public MySQL(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", dbHost, dbPort, dbName, dbUser, dbPassword));
            insertStatement = connection.prepareStatement("INSERT INTO donations(name, country, state, district, transactionId, amountStr, amount, dateStr, date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public void save(Donation donation) {
        try {
            insertStatement.setString(1, donation.name);
            insertStatement.setString(2, donation.country);
            insertStatement.setString(3, donation.state);
            insertStatement.setString(4, donation.district);
            insertStatement.setString(5, donation.transactionId);
            insertStatement.setString(6, donation.amountStr);
            insertStatement.setInt(7, donation.amount);
            insertStatement.setString(8, donation.dateStr);
            insertStatement.setDate(9, donation.date);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
