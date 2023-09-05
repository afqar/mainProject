import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private User consultant;
    private User jobSeeker;
    private LocalDateTime appointmentDateTime;
    private String jobType;

    public Appointment(int id, User consultant, User jobSeeker, LocalDateTime appointmentDateTime, String jobType) {
        this.id = id;
        this.consultant = consultant;
        this.jobSeeker = jobSeeker;
        this.appointmentDateTime = appointmentDateTime;
        this.jobType = jobType;
    }

    // ... Constructors, getters, setters, and other methods ...

    // Save the appointment to the database
    public void save(Connection connection) throws SQLException {
        // Implementation of saving appointment to the database
        // ...
    }

    
}
