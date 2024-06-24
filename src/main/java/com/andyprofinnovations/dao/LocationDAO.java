package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Location;
import com.andyprofinnovations.model.Role;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {
    private Connection con = DBConnection.getConn();


    public boolean addLocation(Location location) throws ServletException, SQLException {
        if (con == null) {
            throw new SQLException("Database connection not successfull");
        }
        String sql = "INSERT INTO location(name, address) VALUES(?,?);";
        int i = 0;

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, location.getName());
        preparedStatement.setString(2, location.getAddress());

        i = preparedStatement.executeUpdate();

        return i > 0;
    }

    public void deleteLocation(int location_id) throws ServletException {
        String sql = "DELETE FROM location WHERE location_id =?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, location_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Location findLocation(String location_id) {
        String sql = "SELECT name,address FROM location WHERE location_id=?;";
        Location location = new Location();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, location_id);
            ResultSet rst = preparedStatement.executeQuery();
            while (rst.next()) {
                location.setLocation_id(Integer.valueOf(rst.getString("location_id")));
                location.setName(rst.getString("name"));
                location.setAddress(rst.getString("address"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return location;
    }

    public void editLocation(Location location) {
        String sql = "update role set name=?, address =? where location_id";
        int i = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, location.getName());
            preparedStatement.setString(2, location.getAddress());
            preparedStatement.setString(3, String.valueOf(location.getLocation_id()));

            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    public List<Location> listLocation() throws SQLException {
//        String sql = "SELECT *  FROM location" ;
//        List<Location> locations = new ArrayList<>();
//        try{
//             PreparedStatement preparedStatement = con.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                int location_id = rs.getInt("location_id");
//                String name = rs.getString("name");
//                locations.add(new Location(location_id, name));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return locations;
//    }

    //    public List<Location> listLocation() throws SQLException {
//        // Initialize an empty list to store Location objects
//        List<Location> locations = new ArrayList<>();
//
//        // SQL query to select all columns from the 'location' table
//        String sql = "SELECT * FROM location";
//
//        // Use try-with-resources to ensure resources are closed after use
//        try (PreparedStatement statement = con.prepareStatement(sql);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            // Iterate through the result set
//            while (resultSet.next()) {
//                // Retrieve data from the current row of the result set
//                int location_id = resultSet.getInt("location_id");
//                String name = resultSet.getString("name");
//                String address = resultSet.getString("address");
//
//                // Create a new Location object and add it to the list
//                Location location = new Location(location_id, name, address);
//                locations.add(location);
//            }
//        }
//
//        // Return the list of Location objects
//        return locations;
//    }
//}
    public List<Location> listLocation() {
        List<Location> list = new ArrayList<>();
        String sql = "SELECT location_id, name, address FROM location";
        try (Connection con = DBConnection.getConn();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Location location = new Location();
                location.setLocation_id(rs.getInt("location_id"));
                location.setName(rs.getString("name"));
                location.setAddress(rs.getString("address")); // Add address here
                list.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}