import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/CreateCertificateServlet")
public class CreateCertificateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String course = request.getParameter("course");
        String grade = request.getParameter("grade");

        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/certificates_db", "username", "password");

            // Insert record into database
            String query = "INSERT INTO certificates (name, course, grade) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, course);
            pst.setString(3, grade);
            pst.executeUpdate();

            // Create Certificate (PDF)
            String certificatePath = createCertificate(name, course, grade);
            
            // Respond back to client
            PrintWriter out = response.getWriter();
            out.println("<h1>Certificate Created Successfully</h1>");
            out.println("<p>Download your certificate: <a href='DownloadCertificateServlet?file=" + certificatePath + "'>Download</a></p>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Example of how you might create a certificate
    private String createCertificate(String name, String course, String grade) {
        // You can use Apache POI or iText to generate a certificate as PDF.
        // Returning a dummy file path for now.
        return "path/to/certificate.pdf";
    }
}
