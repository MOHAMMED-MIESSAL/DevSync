<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.devsync.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.devsync.entity.Task" %>
<%@ page import="com.devsync.entity.Tag" %>
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
<%
    // Récupérer l'utilisateur connecté
    User loggedUser = (User) session.getAttribute("loggedUser");
    boolean isManager = loggedUser != null && loggedUser.getIsManager(); // Supposons que tu as un attribut isManager() dans ta classe User
%>

<form action="${pageContext.request.contextPath}/tasks" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${taskToEdit.id}"/>

    <table id="customers">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Status</th>
            <th>Date Echeance</th>
            <th>User Affected</th>
            <th>Enter Tags (separated by commas):</th>
        </tr>
        <tr>
            <td>
                <input type="text" placeholder="Task Name" value="${taskToEdit.name}" name="task_name" required>
            </td>
            <td>
                <input type="text" placeholder="Task Description" value="${taskToEdit.description}" name="task_description" required>
            </td>
            <td>
                <select name="task_status" required>
                    <option value="NOT_STARTED" ${taskToEdit.status == 'NOT_STARTED' ? 'selected' : ''}>Not Started</option>
                    <option value="IN_PROGRESS" ${taskToEdit.status == 'IN_PROGRESS' ? 'selected' : ''}>In Progress</option>
                    <option value="COMPLETED" ${taskToEdit.status == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                    <option value="ON_HOLD" ${taskToEdit.status == 'ON_HOLD' ? 'selected' : ''}>On Hold</option>
                </select>
            </td>
            <td>
                <input type="date" name="dateEcheance"  required>
            </td>
            <td>
                <%
                    if (isManager) {
                        // Si l'utilisateur est un manager, afficher la liste des utilisateurs
                %>
                <select name="userAffected" required>
                    <option value="">Select User</option>
                    <%
                        List<User> users = (List<User>) request.getAttribute("users");
                        if (users != null) {
                            for (User user : users) {
                    %>
                    <option value="<%= user.getId() %>"><%= user.getFirstName() %> <%= user.getLastName() %></option>
                    <%
                            }
                        }
                    %>
                </select>
                <%
                } else {
                    // Si l'utilisateur n'est pas un manager, utiliser un champ caché avec son propre ID
                %>
                <input type="hidden" name="userAffected" value="<%= loggedUser.getId() %>">
                <p>Affected to : <%= loggedUser.getFirstName() %> <%= loggedUser.getLastName() %></p>
                <%
                    }
                %>
            </td>
            <td>
                <input type="text" id="tags" name="tags" value="${tagNames}" required>
            </td>
        </tr>
    </table>
    <button type="submit">Update Task</button>
</form>
</body>
</html>