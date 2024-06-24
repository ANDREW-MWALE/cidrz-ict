package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Incident;
import com.andyprofinnovations.model.RequestForChange;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestForChangeDAO {

    Connection con = DBConnection.getConn();
    public void AddRequest(RequestForChange requestForChange) throws SQLException {
        int i =0;
        String sql = "INSERT INTO requestForChange(requestNo,logDate,changeDate,DescriptionOfChange,motivation,adInfo,Department) VALUES(?,?,?,?,?,?,?);";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, requestForChange.getRequestNo());
        preparedStatement.setString(2, String.valueOf(requestForChange.getLogDate()));
        preparedStatement.setString(3, String.valueOf(requestForChange.getChangeDate()));
        preparedStatement.setString(4, requestForChange.getDescriptionOfChange());
        preparedStatement.setString(5, requestForChange.getMotivation());
        preparedStatement.setString(6, requestForChange.getAdInfo());
        preparedStatement.setString(7, requestForChange.getDepartment());

        i=preparedStatement.executeUpdate();
    }

    public void deleteRequestForChange(Integer id) throws SQLException {
        int i = 0;
        String sql = "DELETE FROM requestForChange WHERE id=?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        i =preparedStatement.executeUpdate();
    }

    public List<RequestForChange> listRequestForChange() {
        List<RequestForChange> list = new ArrayList<>();
        String sql = "SELECT id, requestNo,logDate,changeDate,DescriptionOfChange,motivation,adInfo,Department FROM requestForChange";
        try {
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(sql);
            while (rst.next()) {
                RequestForChange requestForChange = new RequestForChange();
                requestForChange.setId(rst.getInt("id"));
                requestForChange.setRequestNo(rst.getString("requestNo"));
                requestForChange.setLogDate(rst.getDate("logDate"));
                requestForChange.setDescriptionOfChange("DescriptionOfChange");
                requestForChange.setMotivation(rst.getString("motivation"));
                requestForChange.setAdInfo(rst.getString("Department"));
                list.add(requestForChange);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}

