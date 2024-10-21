<%@ page import="com.devsync.entity.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="com.devsync.entity.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        #customers {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #customers td, #customers th {
            border: 1px solid grey;
            padding: 8px;
        }

        #customers tr:nth-child(even){background-color: #f2f2f2;}

        #customers tr:hover {background-color: #ddd;}

        #customers th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: lightgreen;
            color: white;
        }
    </style>
</head>
<body>

<h1>Tasks List</h1>

<%
    User loggedUser = (User) session.getAttribute("loggedUser");
%>


<button type="button" onclick="location.href='${pageContext.request.contextPath}/tasks?id=new'">Add Task</button>



<table id="customers">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Status</th>
        <th>Creation Date</th>
        <th>Affected User</th>
        <th>Cretor</th>
        <th>Actions</th>
    </tr>
    <%
        List<Task> tasks = (List<Task>) request.getAttribute("tasks");
        for (Task task : tasks) {
    %>
    <tr>
        <td><%= task.getId() %></td>
        <td><%= task.getName() %></td>
        <td><%= task.getDescription() %></td>
        <td><%= task.getStatus() %></td>
        <td><%= task.getDateCreation() %></td>
        <td><%= task.getUserAffected().getId() %></td>
        <td><%= task.getCreator().getId() %></td>


        <td>
            <% if (loggedUser.getIsManager()) { %>

            <button type="button" onclick="location.href='${pageContext.request.contextPath}/tasks?id=<%= task.getId() %>'">Edit</button>
            <form action="${pageContext.request.contextPath}/tasks?id=<%= task.getId() %>" method="post">
                <input type="hidden" name="action" value="delete"/>
                <button type="submit">Delete</button>
            </form>
            <% } %>
            <% if (!loggedUser.getIsManager()) { %>
            <!-- REPLACE  -->
            <form action="${pageContext.request.contextPath}/requestReplacement" method="post">
                <input type="hidden" name="taskId" value="${task.id}">
                <button type="submit">Request Replace</button>
            </form>

            <!-- DELETE  -->
            <form action="${pageContext.request.contextPath}/requestDelete" method="post">
                <input type="hidden" name="taskId" value="${task.id}">
                <button type="submit">Request Delete</button>
            </form>
            <% } %>
        </td>

    </tr>
    <%
        }
    %>

</table>

</body>
</html>


