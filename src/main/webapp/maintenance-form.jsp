<!DOCTYPE html>
<html>
<head>
    <title>Maintenance</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        form {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
        }

        input[type="text"],
        input[type="number"],
        input[type="date"] {
            flex: 1 1 45%; /* Flex-grow, flex-shrink, and base width */
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }

        button[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <form action="MaintainceServlet" method="post">
        <h1>This is maintenance</h1><br>
        <!-- including this to make sure servlet understands what it is doing -->
        <input type="hidden" name="action" value="insert">
        <input type="text" name="gudgetName" placeholder="Gadget Name" required>
        <input type="number" name="serialnumber" placeholder="Serial Number" required>
        <input type="text" name="problemDescription" placeholder="Problem Description" required>
        <input type="text" name="status" placeholder="Status" required>
        <input type="text" name="storageArea" placeholder="Storage Area" required>
        <input type="text" name="broughtBy" placeholder="Brought By" required>
        <input type="text" name="receivedBy" placeholder="Received By" required>
        <input type="text" name="updateby" placeholder="Updated By" required>
        <input type="date" name="dateBrought" placeholder="Date Brought" required>
        <input type="date" name="lastUpDated" placeholder="Last Updated" required>
        <button type="submit">Submit</button>
    </form>
</body>
</html>
