<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.Statement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="com.andyprofinnovations.dao.DBConnection" %>
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
        <form action="IncidentServlet?action=insert" method="post">
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
                       <select id="location" name="location_id" class="form-control" value="<c:out value='${incident    .location_id}' />" required>
                           <option value="">choose your location</option>
                           <%
                               Connection con = null;
                               Statement st = null;
                               ResultSet rs = null;
                               try {
                                   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                   con = DBConnection.getConn();
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
        </form>
    </div>
</body>
</html>