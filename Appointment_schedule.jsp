Appointment_schedule.jsp
<!DOCTYPE html>
<html>
<head>
    <title>Schedule Appointment</title>
</head>
<body>
    <h1>Schedule Appointment</h1>
    <form action="jobconsultancy" method="post">
        <input type="hidden" name="action" value="scheduleAppointment">
        User ID: <input type="text" name="userId" required><br>
        Date and Time: <input type="datetime-local" name="dateTime" required><br>
        Consultant Name: <input type="text" name="consultantName" required><br>
        Job Type: <input type="text" name="jobType" required><br>
        <input type="submit" value="Schedule Appointment">
    </form>
</body>
</html>