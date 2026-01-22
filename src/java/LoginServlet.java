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

            // Load correct MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Railway environment variables
            String host = System.getenv("MYSQLHOST");
            String user = System.getenv("MYSQLUSER");
            String pass = System.getenv("MYSQLPASSWORD");
            String database = System.getenv("MYSQLDATABASE");
            String port = System.getenv("MYSQLPORT");

            String url = "jdbc:mysql://mysql.railway.internal:3306/railway?useSSL=false";

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
