<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Student Fee Dues</title>
</head>
<body>
    <h2>Students with Fee Dues</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Grade</th>
            <th>Fees Due</th>
        </tr>
        <%
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SchoolDB", "root", "password");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Students WHERE fees_due > 0");

                while (rs.next()) {
                    out.println("<tr><td>" + rs.getInt("id") + "</td><td>" + rs.getString("name") + "</td><td>" + rs.getString("grade") + "</td><td>" + rs.getDouble("fees_due") + "</td></tr>");
                }

                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </table>
</body>
</html>
