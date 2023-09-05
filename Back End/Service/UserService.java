import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserService {
    private Connection connection;

    public UserService(Connection connection) {
        this.connection = connection;
        createTable();
    }

    // Create the "users" table if it doesn't exist
    public void createTable() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "username VARCHAR(255) NOT NULL UNIQUE," +
                            "email VARCHAR(255) NOT NULL UNIQUE," +
                            "password VARCHAR(255) NOT NULL," +
                            "role VARCHAR(50) NOT NULL)"
            );
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Register a new user
    public boolean registerUser(String username, String email, String password, String role) {
        if (!isValidUsername(username) || !isValidEmail(email) || !isValidPassword(password)) {
            return false; // Invalid input
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)"
            );
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, role);
            preparedStatement.executeUpdate();
            return true; // User registered successfully
        } catch (SQLException e) {
            handleSQLException(e);
            return false; // User already exists or other error
        }
    }

    // Login a user and return User object if successful
    public Optional<User> loginUser(String username, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                return Optional.of(new User(id, username, email, password, role)); // User found and authenticated
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return Optional.empty(); // User not found or error occurred
    }

    // Get a user by username
    public Optional<User> getUserByUsername(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                return Optional.of(new User(id, username, email, password, role));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return Optional.empty(); // User not found or error occurred
    }

    // Update user details
    public boolean updateUser(int userId, String email, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE users SET email = ?, password = ? WHERE id = ?"
            );
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // User updated successfully
        } catch (SQLException e) {
            handleSQLException(e);
            return false; // Error occurred while updating user
        }
    }

    // Delete a user
    public boolean deleteUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?"
            );
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // User deleted successfully
        } catch (SQLException e) {
            handleSQLException(e);
            return false; // Error occurred while deleting user
        }
    }

    // Validate username using regular expression
    private boolean isValidUsername(String username) {
        return Pattern.matches("^[a-zA-Z0-9_-]{3,16}$", username);
    }

    // Validate email using regular expression
    private boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email);
    }

    // Validate password length
    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    // Handle SQL exceptions
    private void handleSQLException(SQLException e) {
        e.printStackTrace();
    }
}
