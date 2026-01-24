import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            
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

            String query = "SELECT id FROM users WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                HttpSession session = request.getSession();
                session.setAttribute("userid", rs.getInt("id"));
                session.setAttribute("username", username);

                response.sendRedirect("complaints.jsp");

            } else {
                response.sendRedirect("login.jsp?error=1");
            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
            response.sendRedirect("login.jsp?error=2");
        }
    }
}
