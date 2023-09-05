import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
     private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_db_username";
    private static final String DB_PASS = "your_db_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}

class Utils {
    // Parse request body and return as a String
    public static String getRequestBody(HttpExchange exchange) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }
            return requestBody.toString();
        }
    }

    // Parse request body content into a Map<String, String>
    public static Map<String, String> parseRequestBody(String requestBody) {
        Map<String, String> params = new HashMap<>();
        String[] keyValuePairs = requestBody.split("&");
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                params.put(key, value);
            }
        }
        return params;
    }

    // Send HTTP response with status code and message
    public static void sendResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "text/plain");
        exchange.sendResponseHeaders(statusCode, message.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }
}
public class MainApplication {
    public static void main(String[] args) throws IOException {
        // Create instances of the service and controller
        Connection connection = DatabaseConnection.getConnection();
    
        UserService userService = new UserService(userRepository);
        AppointmentService appointmentService = new AppointmentService(appointmentRepository);
        UserController userController = new UserController(userService);
        AppointmentController appointmentController = new AppointmentController(appointmentService);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Define context handlers for routes
        server.createContext("/user/register", new UserRegisterHandler(userController));
        server.createContext("/user/login", new UserLoginHandler(userController));
        // Add more context handlers for other routes

        server.start();
    }
}
class UserRegisterHandler implements HttpHandler {
      private final UserController userController;

    public UserRegisterHandler(UserController userController) {
        this.userController = userController;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Implement logic to handle user registration request
        // You can parse request parameters and call userController.registerUser
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equalsIgnoreCase("POST")) {
            // Parse request parameters from the request body
            String requestBody = Utils.getRequestBody(exchange);
            Map<String, String> params = Utils.parseRequestBody(requestBody);
            String username = params.get("username");
            String email = params.get("email");
            String password = params.get("password");
            String role = params.get("role");
            
            // Call userController.registerUser with parsed parameters
            boolean isRegistered = userController.registerUser(username, email, password, role);

            // Send appropriate response
            if (isRegistered) {
                Utils.sendResponse(exchange, 200, "User registered successfully");
            } else {
                Utils.sendResponse(exchange, 400, "Invalid input or user already exists");
            }
        }
    }
}

class UserLoginHandler implements HttpHandler {
      private final UserController userController;

    public UserLoginHandler(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Implement logic to handle user login request
        // You can parse request parameters and call userController.login
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equalsIgnoreCase("POST")) {
            // Parse request parameters from the request body
            String requestBody = Utils.getRequestBody(exchange);
            Map<String, String> params = Utils.parseRequestBody(requestBody);
            String username = params.get("username");
            String password = params.get("password");
            
            // Call userController.login with parsed parameters
            Optional<User> user = userController.loginUser(username, password);

            // Send appropriate response
            if (user.isPresent()) {
                Utils.sendResponse(exchange, 200, "Login successful");
            } else {
                Utils.sendResponse(exchange, 401, "Invalid credentials or user not found");
            }
        }
    }
}
