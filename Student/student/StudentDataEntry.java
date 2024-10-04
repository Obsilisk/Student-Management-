import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class StudentDataEntry {
    
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student_db";  // Update with your DB details
        String user = "root";  // Your MySQL username
        String password = "Mahi@2110";  // Your MySQL password
        
        String name = "John Doe";
        String fname = "Father Name";
        String mname = "Mother Name";
        String gender = "Male";
        String address = "123 Street";
        String fnum = "1234567890";
        String mnum = "0987654321";
        String snum = "1122334455";
        String email = "student@example.com";
        String photoPath = "path/to/photo.jpg";  // Path to the photo file

        try {
            // Step 1: Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Step 2: Establish a connection
            Connection connection = DriverManager.getConnection(url, user, password);
            
            // Step 3: Create a SQL insert query
            String query = "INSERT INTO students (name, fname, mname, gender, address, fnum, mnum, snum, email, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            // Step 4: Prepare the statement
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
            
            // Set the image file as a binary stream
            File photo = new File(photoPath);
            InputStream inputStream = new FileInputStream(photo);
            statement.setBlob(10, inputStream);
            
            // Step 5: Execute the statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student data was inserted successfully!");
            }
            
            // Step 6: Close connections
            statement.close();
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
