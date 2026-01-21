<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Complaints Dashboard</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <%
        if(session.getAttribute("userid") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/complaint_system", "root", "root75");
            
            String query = "SELECT * FROM complaints WHERE user_id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, (Integer)session.getAttribute("userid"));
            ResultSet rs = pst.executeQuery();
    %>
    <div class="container">
        <div class="header">
            <h2>My Complaints</h2>
            <a href="addComplaint.jsp" class="btn">Add New Complaint</a>
            <a href="LogoutServlet" class="btn">Logout</a>
        </div>
        
        <table class="complaint-table">
            <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Status</th>
                <th>Created Date</th>
                <th>Actions</th>
            </tr>
            <% while(rs.next()) { %>
            <tr>
                <td><%= rs.getString("title") %></td>
                <td><%= rs.getString("description") %></td>
                <td><%= rs.getString("status") %></td>
                <td><%= rs.getTimestamp("created_date") %></td>
                <td>
                    <a href="editComplaint.jsp?id=<%= rs.getInt("id") %>" class="btn-small">Edit</a>
                    <a href="ComplaintServlet?action=delete&id=<%= rs.getInt("id") %>" 
                       onclick="return confirm('Are you sure?')" class="btn-small">Delete</a>
                </td>
            </tr>
            <% } %>
        </table>
    </div>
    <%
        } catch(Exception e) {
            out.println("Error: " + e.getMessage());
        } finally {
            if(conn != null) conn.close();
        }
    %>
</body>
</html>