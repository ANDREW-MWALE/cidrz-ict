package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.RequestForChange;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestForChangeDAO {

    Connection con = DBConnection.getConn();
    public void AddRequest(RequestForChange requestForChange) throws SQLException {
        int i =0;
        String sql = "INSERT INTO requestForChange(requestNo,logDate,changeDate,DescriptionOfChange,motivation,adInfo,Department,createdBy,updatedBy,createdDate,LastUpdatedDate,Approve ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, requestForChange.getRequestNo());
        preparedStatement.setString(2, String.valueOf(requestForChange.getLogDate()));
        preparedStatement.setString(3, String.valueOf(requestForChange.getChangeDate()));
        preparedStatement.setString(4, requestForChange.getDescriptionOfChange());
        preparedStatement.setString(5, requestForChange.getMotivation());
        preparedStatement.setString(6, requestForChange.getAdInfo());
        preparedStatement.setString(7, requestForChange.getDepartment());
        preparedStatement.setString(8, requestForChange.getCreatedBy());
        preparedStatement.setString(9, requestForChange.getUpdatedBy());
        preparedStatement.setString(10, String.valueOf(requestForChange.getCreatedDate()));
        preparedStatement.setString(11, String.valueOf(requestForChange.getLastUpdatedDate()));
        preparedStatement.setString(12, String.valueOf(requestForChange.getApprove()));


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
        String sql = "SELECT id, requestNo,logDate,changeDate,DescriptionOfChange,motivation,adInfo,Department, createdBy, updatedBy,createdDate, LastUpdatedDate FROM requestForChange";
        try {
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(sql);
            while (rst.next()) {
                RequestForChange requestForChange = new RequestForChange();
                requestForChange.setId(rst.getInt("id"));
                requestForChange.setRequestNo(rst.getString("requestNo"));
                requestForChange.setLogDate(rst.getDate("logDate"));
                requestForChange.setDescriptionOfChange(rst.getString("DescriptionOfChange"));
                requestForChange.setMotivation(rst.getString("motivation"));
                requestForChange.setAdInfo(rst.getString("Department"));
                requestForChange.setCreatedBy(rst.getString("createdBy"));
                requestForChange.setUpdatedBy(rst.getString("upDatedBy"));
                requestForChange.setCreatedDate(rst.getDate("createdDate"));
                requestForChange.setLastUpdatedDate(rst.getDate("LastUpdatedDate"));
//                requestForChange.setApprove(rst.getInt("Approve"));
                list.add(requestForChange);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public RequestForChange findRequest(int id) throws SQLException {
        String sql = "SELECT id,requestNo,logDate,changeDate,descriptionOfChange,motivation,adInfo,Department,createdBy, updatedBy,createdDate,LastUpdatedDate,Approve FROM requestForChange where id=? ";

        RequestForChange rfc = new RequestForChange();

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rst =preparedStatement.executeQuery(sql);

        rfc.setRequestNo(rst.getString("id"));
        rfc.setRequestNo(String.valueOf(rst.getInt("requestNo")));
        rfc.setLogDate(rst.getDate("logDate"));
        rfc.setChangeDate(rst.getDate("changeDate"));
        rfc.setDescriptionOfChange(rst.getString("descriptionOfChange"));
        rfc.setMotivation(rst.getString("motivation"));
        rfc.setAdInfo(rst.getString("adInfo"));
        rfc.setDepartment(rst.getString("Department"));
        rfc.setCreatedBy(rst.getString("createdBy"));
        rfc.setUpdatedBy(rst.getString("updatedBy"));
        rfc.setCreatedDate(rst.getDate("createdDate"));
        rfc.setLastUpdatedDate(rst.getDate("LastUpdatedDate"));
        rfc.setApprove(rst.getInt("Approve"));


        return rfc;
    }

    public boolean editRequest(RequestForChange requestForChange) throws SQLException {
        String sql = "UPDATE requestForChange set requestNo=?,logDate=?,changeDate=?,descriptionOfChange=?,motivation=?,adInfo=?,Department=?,createdBy=?, updatedBy=?,createdDate=?,LastUpdatedDate=?,Approve=? WHERE id=?";
        int i = 0;
        PreparedStatement preparedStatement = con.prepareStatement(sql);

        preparedStatement.setString(1, requestForChange.getRequestNo());
        preparedStatement.setDate(2, requestForChange.getLogDate());
        preparedStatement.setDate(3, requestForChange.getChangeDate());
        preparedStatement.setString(4, requestForChange.getDescriptionOfChange());
        preparedStatement.setString(5, requestForChange.getMotivation());
        preparedStatement.setString(6, requestForChange.getAdInfo());
        preparedStatement.setString(7, requestForChange.getDepartment());
        preparedStatement.setString(8, requestForChange.getCreatedBy());
        preparedStatement.setString(7, requestForChange.getUpdatedBy());
        preparedStatement.setDate(8, requestForChange.getCreatedDate());
        preparedStatement.setDate(9, requestForChange.getLastUpdatedDate());
        preparedStatement.setInt(10, requestForChange.getApprove());

        i = preparedStatement.executeUpdate();

        return i > 0;
    }
}

