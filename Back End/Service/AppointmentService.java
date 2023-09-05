import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern; // Import for regex pattern

public class AppointmentService {
    private Connection connection;

    public AppointmentService(Connection connection) {
        this.connection = connection;
        createTable();
    }

    // Create the "appointments" table if it doesn't exist
    public void createTable() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS appointments (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "user_id INT NOT NULL," +
                            "datetime DATETIME NOT NULL," +
                            "consultant_name VARCHAR(255) NOT NULL," +
                            "job_type VARCHAR(255) NOT NULL," +
                            "FOREIGN KEY (user_id) REFERENCES users(id))"
            );
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Schedule a new appointment
    public boolean scheduleAppointment(int userId, LocalDateTime dateTime, String consultantName, String jobType) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO appointments (user_id, datetime, consultant_name, job_type) VALUES (?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, userId);
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(dateTime));
            preparedStatement.setString(3, consultantName);
            preparedStatement.setString(4, jobType);
            preparedStatement.executeUpdate();
            return true; // Appointment scheduled successfully
        } catch (SQLException e) {
            handleSQLException(e);
            return false; // Error occurred while scheduling appointment
        }
    }

    // Fetch appointments for a specific user
    public List<Appointment> getAppointmentsForUser(int userId) {
        List<Appointment> appointments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM appointments WHERE user_id = ?"
            );
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // ... Process result set and create Appointment objects ...
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return appointments;
    }

    // Delete an appointment
    public boolean deleteAppointment(int userId, int appointmentId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM appointments WHERE id = ? AND user_id = ?"
            );
            preparedStatement.setInt(1, appointmentId);
            preparedStatement.setInt(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Appointment deleted successfully
        } catch (SQLException e) {
            handleSQLException(e);
            return false; // Error occurred while deleting appointment
        }
    }

    // Handle SQL exceptions
    private void handleSQLException(SQLException e) {
        e.printStackTrace();
    }
}
