<!DOCTYPE html>
<html>
<head>
    <title>Maintenance</title>
</head>
<body>
    <h1>This is maintenance</h1>
    <form action="MaintainceServlet" method="post">
    <--including this to make sure servlet understands what is doing -->
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
