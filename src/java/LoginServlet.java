import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("mysql://root:ePwtrFhwKvLffKitMLDkuXTzennndpqW@mysql.railway.internal:3306/railway", "root", "ePwtrFhwKvLffKitMLDkuXTzennndpqW");
            
            String query = "SELECT id FROM users WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()) {
              
                HttpSession session = request.getSession();
                session.setAttribute("userid", rs.getInt("id"));
                session.setAttribute("username", username);
                response.sendRedirect("complaints.jsp");
            } else {
                response.sendRedirect("login.jsp?error=1");
            }
            
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=2");
        }
    }
}