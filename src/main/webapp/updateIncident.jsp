<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.andyprofinnovations.dao.DBConnection" %>
<%@ page import="com.andyprofinnovations.model.Incident" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Incident Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Incident Form</h2>
        <%
        String incident_id = request.getParameter("incident_id");
        Incident incident = new Incident();

        // Database connection and querying
        try (Connection con = DBConnection.getConn();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM incident WHERE incident_id = ?")) {
            ps.setString(1, incident_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    incident.setIncident_id(rs.getInt("incident_id"));
                    incident.setName(rs.getString("name"));
                    incident.setDescription(rs.getString("description"));
                    incident.setCauses(rs.getString("causes"));
                    incident.setLocation_id(rs.getInt("location_id"));
                    incident.setCreated_by(rs.getString("created_by"));
                    incident.setCreated_date(rs.getTimestamp("created_date"));
                    incident.setUpdated_by(rs.getString("updated_by"));
                    incident.setLast_updated_date(rs.getTimestamp("last_updated_date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        %>
        <form action="IncidentServlet?action=update" method="post" id="incidentForm">
            <div class="form-group">
                <input type="hidden" class="form-control" id="incident_id" name="incident_id" value="<%= incident.getIncident_id() %>">
            </div>
            <div class="form-group">
                <label for="name">Incident Name:</label>
                <input type="text" class="form-control" id="name" name="name" value="<%= incident.getName() %>">
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea class="form-control" id="description" name="description" required><%= incident.getDescription() %></textarea>
            </div>
            <div class="form-group">
                <label for="causes">Causes:</label>
                <textarea class="form-control" id="causes" name="causes"><%= incident.getCauses() %></textarea>
            </div>
            <div class="form-group">
                <label for="location">Location:</label>
                <select id="location" name="location_id" class="form-control" required>
                    <option value="">Choose your location</option>
                    <%
                    // Fetching locations
                    try (Connection conLoc = DBConnection.getConn();
                         Statement stmtLoc = conLoc.createStatement();
                         ResultSet rsLoc = stmtLoc.executeQuery("SELECT * FROM location")) {
                        while (rsLoc.next()) {
                            int locationId = rsLoc.getInt("location_id");
                            String locationName = rsLoc.getString("name");
                    %>
                    <option value="<%= locationId %>" <%= incident.getLocation_id() == locationId ? "selected" : "" %>><%= locationName %></option>
                    <%
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    %>
                </select>
            </div>
            <div class="form-group">
                <input type="hidden" class="form-control" id="created_date" name="created_date" value="<%= incident.getCreated_date() %>">
            </div>
            <div class="form-group">
                <input type="hidden" class="form-control" id="last_updated_date" name="last_updated_date" value="<%= incident.getLast_updated_date() %>">
            </div>
            <div class="form-group">
                <input type="hidden" class="form-control" id="created_by" name="created_by" value="<%= incident.getCreated_by() %>">
            </div>
            <div class="form-group">
                <input type="hidden" class="form-control" id="updated_by" name="updated_by" value="<%= incident.getUpdated_by() %>">
            </div>
            <button type="submit" class="btn btn-primary">Update</button>
        </form>
    </div>
</body>
</html>
