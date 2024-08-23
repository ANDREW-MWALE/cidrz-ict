<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Request For Change List</title>
    <!-- Add Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
       <link rel="stylesheet" href="${pageContext.request.contextPath}/css/request_list_style.css">
</head>
<body>
    <div class="navbar">
        <a href="home.jsp">Home</a>
        <a href="incident-form.jsp">Report Incident</a>
    </div>

    <div class="content">
        <h1>Requests</h1>

        <%
            Connection con = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=cidrz_ict", "sa", "12345");
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM requestForChange");

                out.println("<table>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>ID</th>");
                out.println("<th>Request No</th>");
                out.println("<th>Log Date</th>");
                out.println("<th>Change Date</th>");
                out.println("<th>Description Of Change</th>");
                out.println("<th>Motivation</th>");
                out.println("<th>Additional Information</th>");
                out.println("<th>Department</th>");
                out.println("<th>Created By</th>");
                out.println("<th>Updated By</th>");
                out.println("<th>Created Date</th>");
                out.println("<th>Last Updated Date</th>");
                out.println("<th>Actions</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("requestNo") + "</td>");
                    out.println("<td>" + rs.getDate("logDate") + "</td>");
                    out.println("<td>" + rs.getDate("changeDate") + "</td>");
                    out.println("<td>" + rs.getString("descriptionOfChange") + "</td>");
                    out.println("<td>" + rs.getString("motivation") + "</td>");
                    out.println("<td>" + rs.getString("adInfo") + "</td>");
                    out.println("<td>" + rs.getString("department") + "</td>");
                    out.println("<td>" + rs.getString("createdBy") + "</td>");
                    out.println("<td>" + rs.getString("updatedBy") + "</td>");
                    out.println("<td>" + rs.getDate("createdDate") + "</td>");
                    out.println("<td>" + rs.getDate("lastUpdatedDate") + "</td>");
                    out.println("<td class='actions'>");
                    out.println("<a href='updateRequest.jsp?action=edit&id=" + rs.getInt("id") + "'><i class='fas fa-edit'></i> Edit</a>");
                    out.println("<a href='RequestForChangeServlet?action=delete&id=" + rs.getInt("id") + "'><i class='fas fa-trash-alt'></i> Delete</a>");
                    out.println("<select id='approval' name='Aid' required>");
                    out.println("<option value=''>Approval status</option>");

                    Connection con2 = null;
                    Statement st = null;
                    ResultSet rs2 = null;
                    try {
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        con2 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=cidrz_ict", "sa", "12345");
                        st = con2.createStatement();
                        String query = "SELECT * FROM approval";
                        rs2 = st.executeQuery(query);
                        while (rs2.next()) {
                            int approvalId = rs2.getInt("Aid");
                            String approvalName = rs2.getString("name");
                            out.println("<option value='" + approvalId + "'>" + approvalName + "</option>");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (rs2 != null) rs2.close();
                        if (st != null) st.close();
                        if (con2 != null) con2.close();
                    }

                    out.println("</select>");
                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("</tbody>");
                out.println("</table>");
            } catch (Exception e) {
                out.println("Error: " + e.getMessage());
            } finally {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            }
        %>
        <button onclick="location.href='request-list.jsp'">Requests</button>
    </div>
</body>
</html>
