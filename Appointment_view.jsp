<!DOCTYPE html>
<html>
<head>
    <title>View Appointments</title>
</head>
<body>
    <h1>Appointments</h1>
    <table border="1">
        <tr>
            <th>Appointment ID</th>
            <th>User ID</th>
            <th>Date and Time</th>
            <th>Consultant Name</th>
            <th>Job Type</th>
        </tr>
        <!-- Loop through appointments from the backend and display them -->
        <c:forEach items="${appointments}" var="appointment">
            <tr>
                <td>${appointment.id}</td>
                <td>${appointment.userId}</td>
                <td>${appointment.dateTime}</td>
                <td>${appointment.consultantName}</td>
                <td>${appointment.jobType}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>