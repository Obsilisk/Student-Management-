import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class StudentServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/student";
        String user = "root";
        String password = "Mahi@2110";

        String name = request.getParameter("name");
        String fname = request.getParameter("fname");
        String mname = request.getParameter("mname");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String fnum = request.getParameter("fnum");
        String mnum = request.getParameter("mnum");
        String snum = request.getParameter("snum");
        String email = request.getParameter("email");
        Part photoPart = request.getPart("photo");  // Getting the uploaded photo file
        
        try {
            // Load JDBC Driver and establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            
            String query = "INSERT INTO students (name, fname, mname, gender, address, fnum, mnum, snum, email, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, fname);
            statement.setString(3, mname);
            statement.setString(4, gender);
            statement.setString(5, address);
            statement.setString(6, fnum);
            statement.setString(7, mnum);
            statement.setString(8, snum);
            statement.setString(9, email);

            // Handle file upload as blob
            InputStream inputStream = photoPart.getInputStream();
            statement.setBlob(10, inputStream);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                response.getWriter().println("Data Inserted Successfully!");
            }
            
            statement.close();
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
