<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Complaint</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <div class="container">
        <h2>Add New Complaint</h2>
        <form action="ComplaintServlet" method="post" class="form">
            <input type="hidden" name="action" value="add">
            <div class="form-group">
                <label>Title:</label>
                <input type="text" name="title" required>
            </div>
            <div class="form-group">
                <label>Description:</label>
                <textarea name="description" required></textarea>
            </div>
            <div class="form-group">
                <label>Status:</label>
                <select name="status">
                    <option value="New">New</option>
                    <option value="In Progress">In Progress</option>
                    <option value="Resolved">Resolved</option>
                </select>
            </div>
            <button type="submit" class="btn">Submit Complaint</button>
        </form>
        <a href="complaints.jsp" class="btn">Back to Complaints</a>
    </div>
</body>
</html>