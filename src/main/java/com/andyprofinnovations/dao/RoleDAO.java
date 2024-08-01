package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Role;

import javax.servlet.ServletException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
private Connection con = DBConnection.getConn();


    public boolean addRole(Role role) throws ServletException, SQLException {
        System.out.println("this");
        if(con==null){
            throw new SQLException("Database connection not successfull");
        }
        String sql = "INSERT INTO role(name, description) VALUES(?,?);";
        int i =0;

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, role.getName());
        preparedStatement.setString(2, role.getDescription());

       i = preparedStatement.executeUpdate();

        return i>0;
    }

    public void deleteRole(int role_id) throws ServletException  {
       String sql = "DELETE FROM role WHERE role_id =?";
       try{
           PreparedStatement preparedStatement = con.prepareStatement(sql);
           preparedStatement.setInt(1, role_id);
           preparedStatement.executeUpdate();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    public Role findRole(String role_id) {
        String sql = "SELECT name,description FROM role WHERE role_id=?;";
        Role role = new Role();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, role_id);
            ResultSet rst = preparedStatement.executeQuery();
            while (rst.next()) {
                role.setRole_id(Integer.valueOf(rst.getString("role_id")));
                role.setName(rst.getString("name"));
                role.setDescription(rst.getString("description"));
            }
               } catch (SQLException e) {
             throw new RuntimeException(e);
          }
        return role;
       }

    public void editRole(Role role) {
        String sql ="update role set name=?, description where role_id";
        int i = 0;
    try{
        PreparedStatement preparedStatement = con.prepareStatement(sql);

        preparedStatement.setString(1, role.getName());
        preparedStatement.setString(2, role.getDescription());
        preparedStatement.setString(3, String.valueOf(role.getRole_id()));

        i = preparedStatement.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

    public List<Role> listRole() {
        List<Role> list = new ArrayList<>();
        String sql = "select role_id,name,description from role";
        try {
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(sql);
            while (rst.next()) {
               Role role = new Role();
                role.setRole_id(Integer.parseInt(rst.getString("role_id")));
                role.setName(rst.getString("name"));
                role.setDescription(rst.getString("description"));

                list.add(role);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }


}
