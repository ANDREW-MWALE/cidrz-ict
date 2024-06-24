package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    // Method to add a new user to the database
    Connection conn = DBConnection.getConn();

    public void addUser(Users user) {
        String sql = "INSERT INTO users (name, position, email, role_id, password) VALUES (?, ?, ?, ?,?)";
        try (
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPosition());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getRole_id());
            statement.setString(5, user.getPassword()); // Password is already hashed

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Users getUserByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (
                PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password); // Password is already hashed
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Users(
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getString("email"),
                        resultSet.getInt("role_id"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
