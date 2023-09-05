import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private Connection connection;

    public AppointmentDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createAppointment(int userId, LocalDateTime dateTime, String consultantName, String jobType) {
        try {
            String sql = "INSERT INTO appointments (user_id, appointment_datetime, consultant_name, job_type) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setObject(2, dateTime);
            statement.setString(3, consultantName);
            statement.setString(4, jobType);
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Appointment> getAppointmentsForUser(int userId) {
        List<Appointment> appointments = new ArrayList<>();
        try {
            String sql = "SELECT * FROM appointments WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("appointment_id");
                LocalDateTime dateTime = resultSet.getObject("appointment_datetime", LocalDateTime.class);
                String consultantName = resultSet.getString("consultant_name");
                String jobType = resultSet.getString("job_type");
                
                Appointment appointment = new Appointment(appointmentId, userId, dateTime, consultantName, jobType);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public boolean deleteAppointment(int userId, int appointmentId) {
        try {
            String sql = "DELETE FROM appointments WHERE user_id = ? AND appointment_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, appointmentId);
            
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
