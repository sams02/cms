<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Complaint</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <%
        if(session.getAttribute("userid") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int complaintId = Integer.parseInt(request.getParameter("id"));
        String title = "";
        String description = "";
        String status = "";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/complaint_system", "root", "root75");
            
            String query = "SELECT * FROM complaints WHERE id=? AND user_id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, complaintId);
            pst.setInt(2, (Integer)session.getAttribute("userid"));
            
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()) {
                title = rs.getString("title");
                description = rs.getString("description");
                status = rs.getString("status");
            } else {
                response.sendRedirect("complaints.jsp");
                return;
            }
            
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("complaints.jsp");
            return;
        }
    %>
    <div class="container">
        <h2>Edit Complaint</h2>
        <form action="ComplaintServlet" method="post" class="form">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<%= complaintId %>">
            <div class="form-group">
                <label>Title:</label>
                <input type="text" name="title" value="<%= title %>" required>
            </div>
            <div class="form-group">
                <label>Description:</label>
                <textarea name="description" required><%= description %></textarea>
            </div>
            <div class="form-group">
                <label>Status:</label>
                <select name="status">
                    <option value="New" <%= status.equals("New") ? "selected" : "" %>>New</option>
                    <option value="In Progress" <%= status.equals("In Progress") ? "selected" : "" %>>In Progress</option>
                    <option value="Resolved" <%= status.equals("Resolved") ? "selected" : "" %>>Resolved</option>
                </select>
            </div>
            <button type="submit" class="btn">Update Complaint</button>
        </form>
        <a href="complaints.jsp" class="btn">Back to Complaints</a>
    </div>
</body>
</html>