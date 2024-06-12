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
        if(con==null){
            throw new SQLException("Database connection not successfull");
        }
        String sql = "INSERT INTO location(name, address) VALUES(?,?);";
        int i =0;

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, location.getName());
        preparedStatement.setString(2, location.getAddress());

        i = preparedStatement.executeUpdate();

        return i>0;
    }

    public void deleteLocation(int location_id) throws ServletException  {
        String sql = "DELETE FROM location WHERE location_id =?";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, location_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Location findLocation(String location_id) {
        String sql = "SELECT name,address FROM location WHERE location_id=?;";
       Location location= new Location();
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
        String sql ="update role set name=?, address =? where location_id";
        int i = 0;
        try{
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, location.getName());
            preparedStatement.setString(2, location.getAddress());
            preparedStatement.setString(3, String.valueOf(location.getLocation_id()));

            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Location> listLocation() {
        List<Location> list = new ArrayList<>();
        String sql = "select location_id,name,address from location";
        try {
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(sql);
            while (rst.next()) {
               Location location = new Location();
                location.setLocation_id(Integer.parseInt(rst.getString("location_id")));
                location.setName(rst.getString("name"));
                location.setAddress(rst.getString("address"));

                list.add(location);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }
}
