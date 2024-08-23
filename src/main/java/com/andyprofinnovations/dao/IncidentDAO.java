package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Incident;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
import static java.sql.DriverManager.getConnection;

public class IncidentDAO {
    private Connection con = DBConnection.getConn();

    public boolean addIncident(Incident incident) throws IOException, SQLException {
        if (con == null) {
            throw new SQLException("Failed to establish database connection");
        }

        String sql = "INSERT INTO incident(name, description, causes, location_id, created_by, created_date, updated_by, last_updated_date, status) VALUES(?,?,?,?,?,?,?,?,?)";
        int i = 0;
        try {
            // Create a prepared statement
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            // Set parameter values
            preparedStatement.setString(1, incident.getName());
            preparedStatement.setString(2, incident.getDescription());
            preparedStatement.setString(3, incident.getCauses());
            preparedStatement.setInt(4, incident.getLocation_id());
            preparedStatement.setString(5, incident.getCreated_by());
            preparedStatement.setTimestamp(6, incident.getCreated_date());
            preparedStatement.setString(7, incident.getUpdated_by());
            preparedStatement.setTimestamp(8, incident.getLast_updated_date());
            preparedStatement.setString(9, incident.getStatus()); // Set the approval_status

            // Execute the SQL statement
            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                // con.close(); // Uncomment if needed
            }
        }
        return i > 0;
    }

    public boolean editIncident(Incident incident) {
        System.out.println("we are printing");
        String sql = "UPDATE incident SET name = ?, description = ?, causes = ?, location_id = ?, updated_by = ?, last_updated_date = ?, status = ? WHERE incident_id = ?";
        try (
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, incident.getName());
            ps.setString(2, incident.getDescription());
            ps.setString(3, incident.getCauses());
            ps.setInt(4, incident.getLocation_id());
            ps.setString(5, incident.getUpdated_by());
            ps.setTimestamp(6, incident.getLast_updated_date());
            ps.setString(7, incident.getStatus()); // Update the approval_status
            ps.setInt(8, incident.getIncident_id());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteIncident(int incident_id) {
        System.out.println("we are deleting");
        String sql = "DELETE FROM incident WHERE incident_id=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, incident_id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;  // Return true if at least one row was affected
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Incident> listIncident() {
        List<Incident> list = new ArrayList<>();
        String sql = "SELECT incident_id, name, description, causes, location_id, created_by, created_date, updated_by, last_updated_date, status FROM incident";
        try {
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(sql);
            while (rst.next()) {
                Incident incident = new Incident();
                incident.setIncident_id(rst.getInt("incident_id"));
                incident.setName(rst.getString("name"));
                incident.setDescription(rst.getString("description"));
                incident.setCauses(rst.getString("causes"));
                incident.setLocation_id(rst.getInt("location_id"));
                incident.setCreated_by(rst.getString("created_by"));
                incident.setCreated_date(rst.getTimestamp("created_date"));
                incident.setUpdated_by(rst.getString("updated_by"));
                incident.setLast_updated_date(rst.getTimestamp("last_updated_date"));
                incident.setStatus(rst.getString("status")); // Retrieve the approval_status
                list.add(incident);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Incident findIncident(String incident_id) {
        String sql = "SELECT incident_id, name, description, causes, location_id, created_by, created_date, updated_by, last_updated_date, status FROM incident WHERE incident_id=?";
        Incident incident = new Incident();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, incident_id);
            ResultSet rst = preparedStatement.executeQuery();
            while (rst.next()) {
                incident.setIncident_id(rst.getInt("incident_id"));
                incident.setName(rst.getString("name"));
                incident.setDescription(rst.getString("description"));
                incident.setCauses(rst.getString("causes"));
                incident.setLocation_id(rst.getInt("location_id"));
                incident.setCreated_by(rst.getString("created_by"));
                incident.setCreated_date(rst.getTimestamp("created_date"));
                incident.setUpdated_by(rst.getString("updated_by"));
                incident.setLast_updated_date(rst.getTimestamp("last_updated_date"));
                incident.setStatus(rst.getString("status")); // Retrieve the approval_status
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return incident;
    }

    // Retrieve incidents for a specific period
    private List<Incident> getIncidentsForPeriod(String period) {
        List<Incident> list = new ArrayList<>();
        String sql = "";
        switch (period) {
            case "daily":
                sql = "SELECT * FROM incident WHERE CAST(created_date AS DATE) = CAST(GETDATE() AS DATE)";
                break;
            case "weekly":
                sql = "SELECT * FROM incident WHERE DATEPART(week, created_date) = DATEPART(week, GETDATE())";
                break;
            case "monthly":
                sql = "SELECT * FROM incident WHERE MONTH(created_date) = MONTH(GETDATE())";
                break;
            case "quarterly":
                sql = "SELECT * FROM incident WHERE DATEPART(quarter, created_date) = DATEPART(quarter, GETDATE())";
                break;
            case "yearly":
                sql = "SELECT * FROM incident WHERE YEAR(created_date) = YEAR(GETDATE())";
                break;
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(sql);
            while (rst.next()) {
                Incident incident = new Incident();
                incident.setIncident_id(rst.getInt("incident_id"));
                incident.setName(rst.getString("name"));
                incident.setDescription(rst.getString("description"));
                incident.setCauses(rst.getString("causes"));
                incident.setLocation_id(rst.getInt("location_id"));
                incident.setCreated_by(rst.getString("created_by"));
                incident.setCreated_date(rst.getTimestamp("created_date"));
                incident.setUpdated_by(rst.getString("updated_by"));
                incident.setLast_updated_date(rst.getTimestamp("last_updated_date"));
                incident.setStatus(rst.getString("status")); // Retrieve the approval_status
                list.add(incident);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Public methods to call the private method with specific period
    public List<Incident> getDailyIncidents() {
        return getIncidentsForPeriod("daily");
    }

    public List<Incident> getWeeklyIncidents() {
        return getIncidentsForPeriod("weekly");
    }

    public List<Incident> getMonthlyIncidents() {
        return getIncidentsForPeriod("monthly");
    }

    public List<Incident> getQuarterlyIncidents() {
        return getIncidentsForPeriod("quarterly");
    }

    public List<Incident> getYearlyIncidents() {
        return getIncidentsForPeriod("yearly");
    }

    public boolean updateIncidentStatus(int incidentId, String status) {
        String sql = "UPDATE incident SET status = ? WHERE incident_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, incidentId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
