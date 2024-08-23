package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.RequestForChange;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestForChangeDAO {

    private Connection con;

    public RequestForChangeDAO() {
        con = DBConnection.getConn();
    }

    public void addRequest(RequestForChange requestForChange) throws SQLException {
        String sql = "INSERT INTO requestForChange (requestNo, logDate, changeDate, descriptionOfChange, motivation, adInfo, department, createdBy, updatedBy, createdDate, lastUpdatedDate, approve) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, requestForChange.getRequestNo());
            preparedStatement.setDate(2, requestForChange.getLogDate());
            preparedStatement.setDate(3, requestForChange.getChangeDate());
            preparedStatement.setString(4, requestForChange.getDescriptionOfChange());
            preparedStatement.setString(5, requestForChange.getMotivation());
            preparedStatement.setString(6, requestForChange.getAdInfo());
            preparedStatement.setString(7, requestForChange.getDepartment());
            preparedStatement.setString(8, requestForChange.getCreatedBy());
            preparedStatement.setString(9, requestForChange.getUpdatedBy());
            preparedStatement.setDate(10, requestForChange.getCreatedDate());
            preparedStatement.setDate(11, requestForChange.getLastUpdatedDate());
            preparedStatement.setInt(12, requestForChange.getApprove());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteRequestForChange(int id) throws SQLException {
        String sql = "DELETE FROM requestForChange WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<RequestForChange> listRequestForChange() {
        List<RequestForChange> list = new ArrayList<>();
        String sql = "SELECT * FROM requestForChange";
        try (Statement stmt = con.createStatement();
             ResultSet rst = stmt.executeQuery(sql)) {
            while (rst.next()) {
                RequestForChange requestForChange = new RequestForChange();
                requestForChange.setId(rst.getInt("id"));
                requestForChange.setRequestNo(rst.getString("requestNo"));
                requestForChange.setLogDate(rst.getDate("logDate"));
                requestForChange.setChangeDate(rst.getDate("changeDate"));
                requestForChange.setDescriptionOfChange(rst.getString("descriptionOfChange"));
                requestForChange.setMotivation(rst.getString("motivation"));
                requestForChange.setAdInfo(rst.getString("adInfo"));
                requestForChange.setDepartment(rst.getString("department"));
                requestForChange.setCreatedBy(rst.getString("createdBy"));
                requestForChange.setUpdatedBy(rst.getString("updatedBy"));
                requestForChange.setCreatedDate(rst.getDate("createdDate"));
                requestForChange.setLastUpdatedDate(rst.getDate("lastUpdatedDate"));
                requestForChange.setApprove(rst.getInt("approve"));
                list.add(requestForChange);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public RequestForChange findRequest(int id) throws SQLException {
        String sql = "SELECT * FROM requestForChange WHERE id = ?";
        RequestForChange rfc = new RequestForChange();
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rst = preparedStatement.executeQuery()) {
                if (rst.next()) {
                    rfc.setId(rst.getInt("id"));
                    rfc.setRequestNo(rst.getString("requestNo"));
                    rfc.setLogDate(rst.getDate("logDate"));
                    rfc.setChangeDate(rst.getDate("changeDate"));
                    rfc.setDescriptionOfChange(rst.getString("descriptionOfChange"));
                    rfc.setMotivation(rst.getString("motivation"));
                    rfc.setAdInfo(rst.getString("adInfo"));
                    rfc.setDepartment(rst.getString("department"));
                    rfc.setCreatedBy(rst.getString("createdBy"));
                    rfc.setUpdatedBy(rst.getString("updatedBy"));
                    rfc.setCreatedDate(rst.getDate("createdDate"));
                    rfc.setLastUpdatedDate(rst.getDate("lastUpdatedDate"));
                    rfc.setApprove(rst.getInt("approve"));
                }
            }
        }
        return rfc;
    }

    public boolean editRequest(RequestForChange requestForChange) throws SQLException {
        String sql = "UPDATE requestForChange SET requestNo = ?, logDate = ?, changeDate = ?, descriptionOfChange = ?, motivation = ?, adInfo = ?, department = ?, createdBy = ?, updatedBy = ?, createdDate = ?, lastUpdatedDate = ?, approve = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, requestForChange.getRequestNo());
            preparedStatement.setDate(2, requestForChange.getLogDate());
            preparedStatement.setDate(3, requestForChange.getChangeDate());
            preparedStatement.setString(4, requestForChange.getDescriptionOfChange());
            preparedStatement.setString(5, requestForChange.getMotivation());
            preparedStatement.setString(6, requestForChange.getAdInfo());
            preparedStatement.setString(7, requestForChange.getDepartment());
            preparedStatement.setString(8, requestForChange.getCreatedBy());
            preparedStatement.setString(9, requestForChange.getUpdatedBy());
            preparedStatement.setDate(10, requestForChange.getCreatedDate());
            preparedStatement.setDate(11, requestForChange.getLastUpdatedDate());
            preparedStatement.setInt(12, requestForChange.getApprove());
            preparedStatement.setInt(13, requestForChange.getId());
            return preparedStatement.executeUpdate() > 0;
        }
    }
}
