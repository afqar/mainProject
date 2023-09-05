import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserController {
    private UserService userService;

    public UserController(Connection connection) {
        this.userService = new UserService(connection);
    }

    public boolean registerUser(String username, String email, String password, String role) {
        if (!isValidUsername(username) || !isValidEmail(email) || !isValidPassword(password)) {
            return false; // Invalid input
        }

        return userService.registerUser(username, email, password, role);
    }

    public Optional<User> loginUser(String username, String password) {
        return userService.loginUser(username, password);
    }

    public Optional<User> getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public boolean updateUser(int userId, String email, String password) {
        return userService.updateUser(userId, email, password);
    }

    public boolean deleteUser(int userId) {
        return userService.deleteUser(userId);
    }
}