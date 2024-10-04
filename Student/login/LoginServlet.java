import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax..servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/commonlogin")s
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        // JDBC connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/employee";
        String jdbcUsername = "root";
        String jdbcPassword = "Mahi@2110";

        // SQL query to authenticate user
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Create a session for the logged-in user
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);

                // Redirect to appropriate dashboard based on role
                if (role.equals("records")) {
                    response.sendRedirect("record.jsp");
                } else if (role.equals("staff")) {
                    response.sendRedirect("staff.html");
                } else if (role.equals("accountant")) {
                    response.sendRedirect("accountant.html");
                } else if (role.equals("student")) {
                    response.sendRedirect("student.html");
                } else if (role.equals("principal")) {
                    response.sendRedirect("principal.html");
                }

            } else {
                // Invalid credentials
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<h3>Invalid login. Please try again.</h3>");
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
