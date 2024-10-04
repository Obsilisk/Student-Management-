import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewTimetableServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "password");

            String sql = "SELECT * FROM Timetable WHERE teacher_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, teacherId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                response.getWriter().println("Day: " + rs.getString("day"));
                response.getWriter().println("Start Time: " + rs.getTime("start_time"));
                response.getWriter().println("End Time: " + rs.getTime("end_time"));
                response.getWriter().println("Subject: " + rs.getString("subject"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
