package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Maintenance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceDAO {
    private Connection con = DBConnection.getConn();

    public boolean deleteMaintaince(int id) throws SQLException {
        String sql = "DELETE FROM maintenance WHERE id=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
    }

    public List<Maintenance> listMaintenance() throws SQLException {
        List<Maintenance> maintenanceList = new ArrayList<>();
        String sql = "SELECT id, gudget, serialnumber, problemDescription, status, strorageArea,broughtBy,receivedBy, updateby, dateBrought, lastUpDated FROM maintenance";
        try (Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Maintenance maintenance = new Maintenance();
                maintenance.setId(rs.getInt("id")); // Fetch id
                maintenance.setGudgetName(rs.getString("gudgetName"));
                maintenance.setSerialnumber(rs.getInt("serialnumber"));
                maintenance.setProblemDescription(rs.getString("problemDescription"));
                maintenance.setStatus(rs.getString("status"));
                maintenance.setStorageArea(rs.getString("storageArea"));
                maintenance.setBroughtBy(rs.getString("broughtBy"));
                maintenance.setReceivedBy(rs.getString("receivedBy"));
                maintenance.setUpdateby(rs.getString("updateby"));
                maintenance.setDateBrought(rs.getDate("dateBrought"));
                maintenance.setLastUpDated(rs.getDate("lastUpDated"));

                maintenanceList.add(maintenance);
            }
        }
        return maintenanceList;
    }

    public boolean editMaintenance(Maintenance maintenance) throws SQLException {
        String sql = "UPDATE maintenance SET gudgetName=?, serialNumber=?, problemDescription=?, status=?, storageArea=?, broughtBy=?, receivedBy=?, updateBy=?, dateBrought=?, lastUpdated=? WHERE id=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, maintenance.getGudgetName());
            preparedStatement.setInt(2, maintenance.getSerialnumber());
            preparedStatement.setString(3, maintenance.getProblemDescription());
            preparedStatement.setString(4, maintenance.getStatus());
            preparedStatement.setString(5, maintenance.getStorageArea());
            preparedStatement.setString(6, maintenance.getBroughtBy());
            preparedStatement.setString(7, maintenance.getReceivedBy());
            preparedStatement.setString(8, maintenance.getUpdateby());
            preparedStatement.setDate(9, maintenance.getDateBrought());
            preparedStatement.setDate(10, maintenance.getLastUpDated());
            preparedStatement.setInt(11, maintenance.getId());  // Assuming Maintenance has an id field
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
    }

    public Maintenance findMaintenance(int id) throws SQLException {
        String sql = "SELECT * FROM maintenance WHERE id=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Maintenance maintenance = new Maintenance();
                    maintenance.setGudgetName(rs.getString("gudgetName"));
                    maintenance.setSerialnumber(rs.getInt("serialNumber"));
                    maintenance.setProblemDescription(rs.getString("problemDescription"));
                    maintenance.setStatus(rs.getString("status"));
                    maintenance.setStorageArea(rs.getString("storageArea"));
                    maintenance.setBroughtBy(rs.getString("broughtBy"));
                    maintenance.setReceivedBy(rs.getString("receivedBy"));
                    maintenance.setUpdateby(rs.getString("updateBy"));
                    maintenance.setDateBrought(rs.getDate("dateBrought"));
                    maintenance.setLastUpDated(rs.getDate("lastUpdated"));
                    return maintenance;
                } else {
                    return null;
                }
            }
        }
    }

    public void addGudget(Maintenance maintenance) throws SQLException {
        String sql = "INSERT INTO maintenance (gudgetName, serialNumber, problemDescription, status, storageArea, broughtBy, receivedBy, updateBy, dateBrought, lastUpdated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, maintenance.getGudgetName());
            preparedStatement.setInt(2, maintenance.getSerialnumber());
            preparedStatement.setString(3, maintenance.getProblemDescription());
            preparedStatement.setString(4, maintenance.getStatus());
            preparedStatement.setString(5, maintenance.getStorageArea());
            preparedStatement.setString(6, maintenance.getBroughtBy());
            preparedStatement.setString(7, maintenance.getReceivedBy());
            preparedStatement.setString(8, maintenance.getUpdateby());
            preparedStatement.setDate(9, maintenance.getDateBrought());
            preparedStatement.setDate(10, maintenance.getLastUpDated());
            preparedStatement.executeUpdate();
        }
    }
}
