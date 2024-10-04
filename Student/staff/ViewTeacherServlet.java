import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewTeacherServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "password");

            String sql = "SELECT * FROM Teacher WHERE teacher_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, teacherId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                response.getWriter().println("ID: " + rs.getInt("teacher_id"));
                response.getWriter().println("First Name: " + rs.getString("first_name"));
                response.getWriter().println("Last Name: " + rs.getString("last_name"));
                response.getWriter().println("Subject: " + rs.getString("subject"));
                response.getWriter().println("Email: " + rs.getString("email"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
