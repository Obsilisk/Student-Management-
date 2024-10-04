package webdev;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DownloadCertificateServlet")
public class DownloadCertificateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String certPath = request.getParameter("file"); // Fetch the path of the certificate from request
        File certificateFile = new File(certPath);

        if (certificateFile.exists()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + certificateFile.getName() + "\"");

            FileInputStream fileInputStream = new FileInputStream(certificateFile);
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            fileInputStream.close();
            out.close();
        } else {
            response.getWriter().println("<h1>File not found!</h1>");
        }
    }
}
