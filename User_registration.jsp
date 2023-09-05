User registration.jsp
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
</head>
<body>
    <h1>User Registration</h1>
    <form action="jobconsultancy" method="post">
        <input type="hidden" name="action" value="registerUser">
        Username: <input type="text" name="username" required><br>
        Email: <input type="email" name="email" required><br>
        Password: <input type="password" name="password" required><br>
        Role: <input type="text" name="role" required><br>
        <input type="submit" value="Register">
    </form>
</body>
</html>