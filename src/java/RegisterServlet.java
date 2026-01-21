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
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("mysql://root:ePwtrFhwKvLffKitMLDkuXTzennndpqW@mysql.railway.internal:3306/railway", "root", "ePwtrFhwKvLffKitMLDkuXTzennndpqW");
            
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