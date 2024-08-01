package com.andyprofinnovations.dao;

import com.andyprofinnovations.model.Incident;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        String sql = "INSERT INTO incident(name, description, causes, location_id, created_by, created_date, updated_by, last_updated_date) VALUES(?,?,?,?,?,?,?,?)";
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

            // Set the created_date and last_updated_date
            preparedStatement.setTimestamp(6, incident.getCreated_date());
            preparedStatement.setString(7, incident.getUpdated_by());
            preparedStatement.setTimestamp(8, incident.getLast_updated_date());

            // Execute the SQL statement
            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                // con.close();
            }
        }
        return i > 0;
    }


    // Helper method to parse date string into java.sql.Timestamp
    private java.sql.Timestamp parseDateString(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date parsedDate = dateFormat.parse(dateString);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean editIncident(Incident incident) {
        System.out.println("show");

        String sql = "UPDATE incident SET name=?, description=?, causes=?, location_id=?, created_by=?, created_date=?, updated_by=?, last_updated_date=? WHERE incident_id=?";
        int i = 0;

        try (Connection con = DBConnection.getConn();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setString(1, incident.getName());
            preparedStatement.setString(2, incident.getDescription());
            preparedStatement.setString(3, incident.getCauses());
            preparedStatement.setInt(4, incident.getLocation_id());
            preparedStatement.setString(5, incident.getCreated_by());
            preparedStatement.setTimestamp(6, incident.getCreated_date());
            preparedStatement.setString(7, incident.getUpdated_by());
            preparedStatement.setTimestamp(8, incident.getLast_updated_date());
            preparedStatement.setInt(9, incident.getIncident_id());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating incident", e);
        }
        return i > 0;
    }
    public boolean deleteIncident(int incident_id) {
        String sql = "DELETE FROM incident WHERE incident_id=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(valueOf(incident_id)));
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;  // Return true if at least one row was affected
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Incident> listIncident() {
        List<Incident> list = new ArrayList<>();
        String sql = "SELECT incident_id, name, description, causes, location_id, created_by, created_date, updated_by, last_updated_date FROM incident";
        try {
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(sql);
            while (rst.next()) {
                Incident incident = new Incident();
                incident.setIncident_id(Integer.valueOf(rst.getString("incident_id")));
                incident.setName(rst.getString("name"));
                incident.setDescription(rst.getString("description"));
                incident.setCauses(rst.getString("causes"));
                incident.setLocation_id(Integer.parseInt(rst.getString("location_id")));
                incident.setCreated_by(rst.getString("created_by"));
                incident.setCreated_date(Timestamp.valueOf(rst.getString("created_date")));
                incident.setUpdated_by(rst.getString("updated_by"));
                incident.setLast_updated_date(Timestamp.valueOf(rst.getString("last_updated_date")));
                list.add(incident);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Incident findIncident(String incident_id) {
        String sql = "select Incident_id,name,description,causes,location_id,created_by,created_date,updated_by,last_updated_date from incident where Incident_id=?";
//        Location location = new Location();
        Incident incident = new Incident();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, incident_id);
            ResultSet rst = preparedStatement.executeQuery();
            while (rst.next()) {
//                Incident incident = new Incident();
                incident.setIncident_id(Integer.valueOf(rst.getString("incident_id")));
                incident.setName(rst.getString("name"));
                incident.setDescription(rst.getString("description"));
                incident.setCauses(rst.getString("causes"));
                incident.setLocation_id(Integer.parseInt(rst.getString("location_id")));
                incident.setCreated_by(rst.getString("created_by"));
                incident.setCreated_date(Timestamp.valueOf(rst.getString("created_date")));
                incident.setUpdated_by(rst.getString("updated_by"));
                incident.setLast_updated_date(Timestamp.valueOf(rst.getString("last_updated_date")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return incident;
    }

}
