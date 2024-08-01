<%@ page import="com.andyprofinnovations.dao.DBConnection" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.andyprofinnovations.model.RequestForChange" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Change Request Form</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/requestform.css">
</head>
<body>
    <div class="container">
    <h2>Change Request Form</h2>
    <%
        String id = request.getParameter("id");
        RequestForChange requestForChange = new RequestForChange();
        if (id != null && !id.isEmpty()) {
            try {
                String sql = "SELECT * FROM requestForChange WHERE id=?";
                Connection con = DBConnection.getConn();
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, Integer.parseInt(id));
                ResultSet rst = preparedStatement.executeQuery();
                if (rst.next()) {
                    requestForChange.setRequestNo(String.valueOf(rst.getInt("requestNo")));
                    requestForChange.setLogDate(rst.getDate("logDate"));
                    requestForChange.setChangeDate(rst.getDate("changeDate"));
                    requestForChange.setDescriptionOfChange(rst.getString("descriptionOfChange"));
                    requestForChange.setMotivation(rst.getString("motivation"));
                    requestForChange.setAdInfo(rst.getString("adInfo"));
                    requestForChange.setDepartment(rst.getString("Department"));
                    requestForChange.setApprove(rst.getInt("Approve"));
                }
                rst.close();
                preparedStatement.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    %>
        <form action="RequestForChangeServlet?action=update" method="post" class="flex-form" id="requestForChangeForm">
            <div class="form-group">
                <input type="hidden" class="form-control" id="id" name="id" value="<%= id %>">
            </div>
            <div class="form-group">
                <label for="requestNo">Request No:</label>
                <input type="text" class="form-control" id="requestNo" name="requestNo" value="<%=requestForChange.getRequestNo() %>" required>
            </div>
            <div class="form-group">
                <label for="logDate">Log Date:</label>
                <input type="date" class="form-control" id="logDate" name="logDate" value="<%=requestForChange.getLogDate() %>" required>
            </div>
            <div class="form-group">
                <label for="changeDate">Change Date:</label>
                <input type="date" class="form-control" id="changeDate" name="changeDate" value="<%=requestForChange.getChangeDate() %>" required>
            </div>
            <div class="form-group">
                <label for="descriptionOfChange">Description Of Change:</label>
                <textarea class="form-control" id="descriptionOfChange" name="descriptionOfChange" required><%=requestForChange.getDescriptionOfChange() %></textarea>
            </div>
            <div class="form-group">
                <label for="motivation">Motivation:</label>
                <textarea class="form-control" id="motivation" name="motivation"><%=requestForChange.getMotivation() %></textarea>
            </div>
            <div class="form-group">
                <label for="adInfo">Additional Information:</label>
                <textarea class="form-control" id="adInfo" name="adInfo"><%=requestForChange.getAdInfo() %></textarea>
            </div>
            <div class="form-group">
                <label for="Department">Department:</label>
                <input type="text" class="form-control" id="Department" name="Department" value="<%=requestForChange.getDepartment() %>">
            </div>
            <div class="form-group">
                <label for="Approve">Approve:</label>
                <input type="number" class="form-control" id="Approve" name="Approve" value="<%=requestForChange.getApprove() %>">
            </div>
            <button type="submit" class="btn btn-primary btn-lg">Update</button>
        </form>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
