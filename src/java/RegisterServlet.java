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

        Connection conn = null;

        try {

            // Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Railway Environment Variables
            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String database = System.getenv("MYSQLDATABASE");
            String user = System.getenv("MYSQLUSER");
            String pass = System.getenv("MYSQLPASSWORD");

            // Fallback (Local Testing)
            if (host == null) {
                host = "mysql.railway.internal";
                port = "3306";
                database = "railway";
                user = "root";
                pass = "WlnhxFRdYRheORYcVxEsjSdyHoIOPPBG";
            }

            // Correct JDBC URL
            String url = "jdbc:mysql://mysql.railway.internal:3306/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            conn = DriverManager.getConnection(url, user, pass);

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
