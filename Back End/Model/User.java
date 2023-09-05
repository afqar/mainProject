import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.SQLException;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private UserRole role; // Enum for user roles

    public User(int id, String username, String email, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ... Constructors, getters, setters, and other methods ...

    // Validate email using regular expression
    public static boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email);
    }

    // Validate password length
    public static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    // Save the user to the database
    public void save(Connection connection) throws SQLException {
        // Implementation of saving user to the database
        // ...
    }

    // Retrieve a user from the database by username
    public static User getByUsername(Connection connection, String username) throws SQLException {
        // Implementation of retrieving user by username from the database
        // ...
    }

    // Other methods for updating and deleting users if needed
}
