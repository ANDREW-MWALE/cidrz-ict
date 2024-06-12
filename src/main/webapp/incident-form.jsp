<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        <a href="incident-list.jsp" class="btn-link">Report your incident</a>
        <form action="IncidentServlet" method="post">
            <div class="form-group">
                <label for="name">Incident Name:</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea class="form-control" id="description" name="description" required></textarea>
            </div>
            <div class="form-group">
                <label for="causes">Causes:</label>
                <textarea class="form-control" id="causes" name="causes" required></textarea>
            </div>
            <div class="form-group">
                <label for="location_id">Location ID:</label>
                <select class="form-control" id="location_id" name="location_id" required>
                    <option value="1">headquaters</option>
                    <option value="2">kalingalinga lab</option>
                    <option value="3">kanyama clinic</option>
                    <option value="4">chilenje</option>
                    <option value="5">chingola</option>
                    <!-- Populate with actual locations -->
                </select>
            </div>
            <button type="submit" class="btn-primary">Submit</button>
        </form>
    </div>
</body>
</html>