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
        <h2>Location Form</h2>
        <form action="LocationServlet" method="post">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" class="form-control" id="address" name="address" required>
            </div>
            <button type="submit" class="btn-primary">Submit</button>
        </form>
    </div>
</body>
</html>
