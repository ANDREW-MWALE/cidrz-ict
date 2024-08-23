package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Maintenance;

import javax.servlet.RequestDispatcher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceDAO {
    private Connection con = DBConnection.getConn();

    public boolean deleteMaintaince(int id) throws SQLException {
        String sql = "delete from maintenance where id=?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        int affectedRows=preparedStatement.executeUpdate();
       return affectedRows > 0;
    }

    public List<Maintenance> listMaintance() throws SQLException {
        String sql = "SELECT * FROM maintenance";
        List<Maintenance> list = new ArrayList<>();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {

            Maintenance maintenance = new Maintenance();

            maintenance.setGudgetName(resultSet.getString("gudgetName"));
            maintenance.setSerialnumber(resultSet.getInt("serialnumber"));
            maintenance.setProblemDescription(resultSet.getString("problemDescription"));
            maintenance.setStatus(resultSet.getString("status"));
            maintenance.setBroughtBy(resultSet.getString("storageArea"));
            maintenance.setBroughtBy(resultSet.getString("broughtBy"));
            maintenance.setReceivedBy(resultSet.getString("receivedBy"));
            maintenance.setUpdateby(resultSet.getString("updatedBy"));
            maintenance.setDateBrought(resultSet.getString("dateBrought"));
            maintenance.setLastUpDated(resultSet.getString("lastUpDate"));
           list.add(maintenance);
        } 
        return list;
    }

    public void editMaintenance(Maintenance maintaince) {

    }

    public Maintenance findMaintenance(String id) {
        return null;
    }

    public void addGudget(Maintenance maintaince) throws SQLException {
        String sql = "INSERT INTO maintenance(gudgetName,serialnumber,problemDescription, status,storageArea,broughtBy,receivedBy,updatedBy,dateBrought,lastUpDate)VALUES(?,?,?,?,?,?,?,?,?,?)";
        int i = 0;
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, maintaince.getGudgetName());
        preparedStatement.setString(2, String.valueOf(maintaince.getSerialnumber()));
        preparedStatement.setString(3, maintaince.getProblemDescription());
        preparedStatement.setString(4, maintaince.getStatus());
        preparedStatement.setString(5, maintaince.getStorageArea());
        preparedStatement.setString(6, maintaince.getBroughtBy());
        preparedStatement.setString(7, maintaince.getReceivedBy());
        preparedStatement.setString(8, maintaince.getUpdateby());
        preparedStatement.setString(9, maintaince.getDateBrought());
        preparedStatement.setString(10, maintaince.getLastUpDated());

        i= preparedStatement.executeUpdate();
    }
}
