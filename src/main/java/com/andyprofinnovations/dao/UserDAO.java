package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Users> userList() {
        List<Users> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try{
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(sql);

            while (rst.next()){
            Users user= new Users();
            user.setUser_id(rst.getInt("user_id"));
            user.setName(rst.getString("name"));
            user.setPosition(rst.getString("position"));
            user.setEmail(rst.getString("email"));
            user.setPassword(rst.getString("password"));

            users.add(user);

                           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean editUpdateUser(Users users) throws SQLException {
        String sql = "Update users set name=?, position=?, position=?, email=? role_id=? password=? where user_id=?";
        int i = 0;
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, users.getName());
        preparedStatement.setString(2, users.getPosition());
        preparedStatement.setString(3, users.getEmail());
        preparedStatement.setString(4, String.valueOf(users.getRole_id()));
        preparedStatement.setString(5, users.getPassword());

        i = preparedStatement.executeUpdate();
        return i>0;
    }

    public void deleteUsers(String userId) {
    }
}
