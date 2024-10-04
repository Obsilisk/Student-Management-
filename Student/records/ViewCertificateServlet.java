package webdev;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;

@WebServlet("/ViewCertificateServlet")
public class ViewCertificateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String certId = request.getParameter("certId");

        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/certificates_db", "username", "password");

            // Fetch certificate details from database
            String query = "SELECT * FROM certificates WHERE id=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, certId);
            ResultSet rs = pst.executeQuery();

            PrintWriter out = response.getWriter();

            if (rs.next()) {
                String name = rs.getString("name");
                String course = rs.getString("course");
                String grade = rs.getString("grade");

                out.println("<h1>Certificate Details</h1>");
                out.println("<p>Name: " + name + "</p>");
                out.println("<p>Course: " + course + "</p>");
                out.println("<p>Grade: " + grade + "</p>");
                out.println("<p><a href='DownloadCertificateServlet?certId=" + certId + "'>Download Certificate</a></p>");
            } else {
                out.println("<h1>Certificate Not Found</h1>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
