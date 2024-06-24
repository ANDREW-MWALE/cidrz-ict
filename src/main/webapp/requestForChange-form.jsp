<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Change Request Form</title>
    <!-- Your CSS styles -->
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
            display: flex;
            align-items: center;
        }
        .form-group label {
            font-weight: bold;
            margin-right: 10px;
            flex: 1;
        }
        .form-control {
            flex: 2;
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
    </style>
</head>
<body>
    <div class="container">
        <h2>Change Request Form</h2>
        <form action="RequestForChangeServlet" method="post">
            <div class="form-group">
                <label for="requestNo">Request No:</label>
                <input type="text" class="form-control" id="requestNo" name="requestNo" required>
            </div>
            <div class="form-group">
                <label for="logDate">Log Date:</label>
                <input type="date" class="form-control" id="logDate" name="logDate" required>
            </div>
            <div class="form-group">
                <label for="changeDate">Change Date:</label>
                <input type="date" class="form-control" id="changeDate" name="changeDate" required>
            </div>
            <div class="form-group">
                <label for="descriptionOfChange">Description Of Change:</label>
                <textarea class="form-control" id="descriptionOfChange" name="descriptionOfChange" required></textarea>
            </div>
            <div class="form-group">
                <label for="motivation">Motivation:</label>
                <textarea class="form-control" id="motivation" name="motivation"></textarea>
            </div>
            <div class="form-group">
                <label for="adInfo">Additional Information:</label>
                <textarea class="form-control" id="adInfo" name="adInfo"></textarea>
            </div>
            <div class="form-group">
                <label for="Department">Department:</label>
                <input type="text" class="form-control" id="Department" name="Department">
            </div>
            <button type="submit" class="btn-primary">Submit</button>
        </form>
    </div>
</body>
</html>
