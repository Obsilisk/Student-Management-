package webdev.fee management;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class FeeServlet extends HttpServlet {

    // JDBC connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/FeeManagement";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String paymentAmountStr = request.getParameter("paymentAmount");
        double paymentAmount = Double.parseDouble(paymentAmountStr);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Update payment and fee due in the database
            String updateFeeSQL = "UPDATE students SET fee_paid = fee_paid + ?, fee_due = fee_due - ? WHERE student_id = ?";
            PreparedStatement updateFeeStmt = conn.prepareStatement(updateFeeSQL);
            updateFeeStmt.setDouble(1, paymentAmount);
            updateFeeStmt.setDouble(2, paymentAmount);
            updateFeeStmt.setInt(3, Integer.parseInt(studentId));
            updateFeeStmt.executeUpdate();

            // Insert payment history
            String insertPaymentSQL = "INSERT INTO payments (student_id, amount_paid, payment_date) VALUES (?, ?, NOW())";
            PreparedStatement insertPaymentStmt = conn.prepareStatement(insertPaymentSQL);
            insertPaymentStmt.setInt(1, Integer.parseInt(studentId));
            insertPaymentStmt.setDouble(2, paymentAmount);
            insertPaymentStmt.executeUpdate();

            // Fetch the updated fee due
            String fetchDueSQL = "SELECT fee_due FROM students WHERE student_id = ?";
            PreparedStatement fetchDueStmt = conn.prepareStatement(fetchDueSQL);
            fetchDueStmt.setInt(1, Integer.parseInt(studentId));
            ResultSet rs = fetchDueStmt.executeQuery();
            if (rs.next()) {
                double feeDue = rs.getDouble("fee_due");
                out.println("<h3>Updated Fee Due: " + feeDue + "</h3>");
            }

            // Fetch and display payment history
            String fetchHistorySQL = "SELECT amount_paid, payment_date FROM payments WHERE student_id = ?";
            PreparedStatement fetchHistoryStmt = conn.prepareStatement(fetchHistorySQL);
            fetchHistoryStmt.setInt(1, Integer.parseInt(studentId));
            ResultSet historyRS = fetchHistoryStmt.executeQuery();
            
            out.println("<h2>Payment History</h2><ul>");
            while (historyRS.next()) {
                double amountPaid = historyRS.getDouble("amount_paid");
                Date paymentDate = historyRS.getDate("payment_date");
                out.println("<li>Amount Paid: " + amountPaid + " on " + paymentDate + "</li>");
            }
            out.println("</ul>");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

