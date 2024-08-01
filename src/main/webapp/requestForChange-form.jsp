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
        <form action="RequestForChangeServlet" method="post" class="flex-form">
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
             <div class="form-group">
                 <label for="Approve">Approve:</label>
                  <input type="number" class="form-control" id="Approve" name="Approve">
             </div>
            <button type="submit" class="btn-primary">Submit</button>
        </form>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
