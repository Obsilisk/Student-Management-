import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddTimetableServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        String day = request.getParameter("day");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String subject = request.getParameter("subject");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "password");

            String sql = "INSERT INTO Timetable (teacher_id, day, start_time, end_time, subject) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, teacherId);
            statement.setString(2, day);
            statement.setString(3, startTime);
            statement.setString(4, endTime);
            statement.setString(5, subject);

            statement.executeUpdate();
            conn.close();

            response.getWriter().println("Timetable added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
