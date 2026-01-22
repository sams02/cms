import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        
            String host = System.getenv("mysql.railway.internal");
            String port = System.getenv("3306");
            String database = System.getenv("railway");
            String name = System.getenv("root");
            String pass = System.getenv("ePwtrFhwKvLffKitMLDkuXTzennndpqW");

// Railway Internal Network Fallback
          if (host == null) {
               host = "mysql.railway.internal";
               port = "3306";
               database="railway";
               name="root";
               pass="WlnhxFRdYRheORYcVxEsjSdyHoIOPPBG";
          }
          
          String url = "mysql://root:WlnhxFRdYRheORYcVxEsjSdyHoIOPPBG@mysql.railway.internal:3306/railway";
          
          Connection conn = null;
          try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              conn = DriverManager.getConnection(url, name, pass);
           
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, email);
            
            pst.executeUpdate();
            conn.close();
            
            response.sendRedirect("login.jsp?registered=1");
        } catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=1");
        }
    }
}