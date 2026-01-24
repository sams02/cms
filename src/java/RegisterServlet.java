import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
        // Railway environment variables
        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String user = System.getenv("MYSQLUSER");
        String pass = System.getenv("MYSQLPASSWORD");

        // Local/test defaults (host/port/database only)
        if (host == null || host.isEmpty()) host = "mysql.railway.internal";
        if (port == null || port.isEmpty()) port = "3306";
        if (database == null || database.isEmpty()) database = "railway";

        // Credentials must be provided via env in production
          if (user == null || user.isEmpty() || pass == null || pass.isEmpty()) {
                 throw new ServletException("Database credentials (MYSQLUSER/MYSQLPASSWORD) are not set");
            }

           String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true", host, port, database);
           Connection conn = DriverManager.getConnection(url, user, pass);
           
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, email);

            pst.executeUpdate();

            conn.close();

            response.sendRedirect("login.jsp?registered=1");

        } catch (Exception e) {

            e.printStackTrace();
            response.sendRedirect("register.jsp?error=1");

        }
    }
}
