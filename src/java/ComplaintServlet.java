import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ComplaintServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("userid") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("mysql://root:ePwtrFhwKvLffKitMLDkuXTzennndpqW@mysql.railway.internal:3306/railway", "root", "ePwtrFhwKvLffKitMLDkuXTzennndpqW");
            
            if("add".equals(action)) {
                String query = "INSERT INTO complaints (user_id, title, description, status, created_date) VALUES (?, ?, ?, ?, NOW())";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, (Integer)session.getAttribute("userid"));
                pst.setString(2, request.getParameter("title"));
                pst.setString(3, request.getParameter("description"));
                pst.setString(4, request.getParameter("status"));
                pst.executeUpdate();
            }
            else if("update".equals(action)) {
                String query = "UPDATE complaints SET title=?, description=?, status=? WHERE id=? AND user_id=?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, request.getParameter("title"));
                pst.setString(2, request.getParameter("description"));
                pst.setString(3, request.getParameter("status"));
                pst.setInt(4, Integer.parseInt(request.getParameter("id")));
                pst.setInt(5, (Integer)session.getAttribute("userid"));
                pst.executeUpdate();
            }
            
            conn.close();
            response.sendRedirect("complaints.jsp");
        } catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("complaints.jsp?error=1");
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("userid") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        if("delete".equals(action)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/complaint_system", "root", "root75");
                
                String query = "DELETE FROM complaints WHERE id=? AND user_id=?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, Integer.parseInt(request.getParameter("id")));
                pst.setInt(2, (Integer)session.getAttribute("userid"));
                pst.executeUpdate();
                
                conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("complaints.jsp");
        }
    }
}