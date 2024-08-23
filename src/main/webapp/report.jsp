<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Incident Report</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
</head>
<body>
    <h1>Incident Report - ${period}</h1>
    <table>
        <thead>
            <tr>
                <th>Incident ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Causes</th>
                <th>Location ID</th>
                <th>Created By</th>
                <th>Created Date</th>
                <th>Updated By</th>
                <th>Last Updated Date</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="incident" items="${incidents}">
                <tr>
                    <td>${incident.incident_id}</td>
                    <td>${incident.name}</td>
                    <td>${incident.description}</td>
                    <td>${incident.causes}</td>
                    <td>${incident.location_id}</td>
                    <td>${incident.created_by}</td>
                    <td>${incident.created_date}</td>
                    <td>${incident.updated_by}</td>
                    <td>${incident.last_updated_date}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="incident-list.jsp">Back to Incident List</a>
</body>
</html>
