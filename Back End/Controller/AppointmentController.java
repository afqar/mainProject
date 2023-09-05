import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentController {
    private AppointmentService appointmentService;

    public AppointmentController(Connection connection) {
        this.appointmentService = new AppointmentService(connection);
    }

    public boolean scheduleAppointment(int userId, LocalDateTime dateTime, String consultantName, String jobType) {
        return appointmentService.scheduleAppointment(userId, dateTime, consultantName, jobType);
    }

    public List<Appointment> getAppointmentsForUser(int userId) {
        return appointmentService.getAppointmentsForUser(userId);
    }

    public boolean deleteAppointment(int userId, int appointmentId) {
        return appointmentService.deleteAppointment(userId, appointmentId);
    }
}
