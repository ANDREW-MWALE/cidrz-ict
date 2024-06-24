<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.Statement, java.sql.ResultSet, java.sql.SQLException" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Incident Form</title>
   <style>
           body {
               background-color: #f4f4f4;
               font-family: Arial, sans-serif;
               display: flex;
               justify-content: center;
               align-items: center;
               height: 100vh;
               margin: 0;
               padding: 20px;
           }
           .container {
               background-color: #fff;
               padding: 40px;
               border-radius: 10px;
               box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
               width: 100%;
               max-width: 600px;
           }
           h2 {
               margin-bottom: 20px;
               text-align: center;
               color: #000080;
           }
           .form-group {
               margin-bottom: 15px;
           }
           .form-group label {
               font-weight: bold;
               display: block;
               margin-bottom: 5px;
           }
           .form-control {
               width: 100%;
               padding: 8px;
               margin-bottom: 10px;
               border: 1px solid #b3d1ff;
               border-radius: 5px;
               box-sizing: border-box;
               font-size: 1em;
           }
           textarea.form-control {
               height: 100px;
           }
           .btn-primary {
               display: inline-block;
               font-size: 1em;
               padding: 8px 16px;
               border-radius: 5px;
               background-color: #000080;
               color: white;
               border: none;
               cursor: pointer;
               text-align: center;
               width: 100%;
           }
           .btn-primary:hover {
               background-color: #0000cc;
           }
           .btn-link {
               color: #000080;
               text-decoration: none;
               margin-bottom: 15px;
               display: inline-block;
           }
           .btn-link:hover {
               text-decoration: underline;
           }
       </style>
</head>
<body>
    <div class="container">
        <h2>Incident Form</h2>
        <form action="IncidentServlet" method="post">
            <div class="form-group">
                <label for="name">Incident Name:</label>
                <input type="text" class="form-control" id="name" name="name" value="<c:out value='${incident.name}' />" required>
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea class="form-control" id="description" name="description" value="<c:out value='${incident.description}' />"required></textarea>
            </div>
            <div class="form-group">
                <label for="causes">Causes:</label>
                <textarea class="form-control" id="causes" name="causes" value="<c:out value='${incident.causes}' />" required></textarea>
            </div>
            <div class="form-group">
                           <label for="location">Location:</label>
                       <select id="location" name="location_id" class="form-control" value="<c:out value='${incident.location_id}' />" required>
                           <option value="">choose your location</option>
                           <%
                               Connection con = null;
                               Statement st = null;
                               ResultSet rs = null;
                               try {
                                   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                   con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=cidrz_ict", "sa", "12345");
                                   st = con.createStatement();
                                   String query = "SELECT * FROM location";
                                   rs = st.executeQuery(query);
                                   while (rs.next()) {
                                       int locationId = rs.getInt("location_id");
                                       String locationName = rs.getString("name");
                           %>
                           <option value="<%= locationId %>"><%= locationName %></option>
                           <%
                                   }
                               } catch (Exception e) {
                                   e.printStackTrace();
                               } finally {
                                   try {
                                       if (rs != null) rs.close();
                                       if (st != null) st.close();
                                       if (con != null) con.close();
                                   } catch (SQLException e) {
                                       e.printStackTrace();
                                   }
                               }
                           %>
                       </select>

                       </div>
            <button type="submit" class="btn-primary">${empty incident ? 'Submit' : 'Update'}</button>
            <%-- Hidden field for incident ID, if updating --%>
            <input type="hidden" name="incident_id" value="${empty incident ? '' : incident.incident_id}" />
        </form>
    </div>
</body>
</html>
