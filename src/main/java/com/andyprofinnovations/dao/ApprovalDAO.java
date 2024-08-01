package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Approval;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApprovalDAO {

    Connection con = DBConnection.getConn();

    public void addApproval(Approval approval) throws SQLException {
        int i = 0;

        String sql = "INSERT INTO approval(name)VALUES(?)";

        PreparedStatement preparedStatement = con.prepareStatement(sql);

        preparedStatement.setString(1, approval.getName());
        i = preparedStatement.executeUpdate();

    }

    public List<Approval> listApprovals() throws SQLException {
        List<Approval> list= new ArrayList<>();

        String sql = "select Aid, name from approval";
        Statement statement = con.createStatement();
        ResultSet rst =  statement.executeQuery(sql);
        while (rst.next()) {
            Approval approval = new Approval();
            approval.setAid(rst.getInt("Aid"));
            approval.setName(rst.getString("name"));
            list.add(approval);
        }
        return list;
    }
    // Here we delete values from the database
    public boolean deleteApproval(String aid) throws SQLException {
        String sql = "delete from approval where Aid=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, Integer.parseInt(aid));
        int affectRows = preparedStatement.executeUpdate();
        return affectRows>0;

    }

    public boolean editApproval(Approval approval) throws SQLException {
        int i = 0;
        String sql = "UPDATE approval set name =? where Aid =?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, approval.getName());
        i = preparedStatement.executeUpdate();

        return i>0;

    }

    public Approval findApproval(int approvalAid) throws SQLException {

        String sql = "SELECT id, name FROM approval where Aid = ?";

        Approval approval = new Approval();

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(approvalAid));
        ResultSet rst = preparedStatement.executeQuery();

        while (rst.next()) {
            approval.setAid(Integer.parseInt(rst.getString("Aid")));
            approval.setName(rst.getString("name"));

        }
        return approval;
    }
}
