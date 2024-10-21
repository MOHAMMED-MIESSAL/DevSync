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





<table id="customers">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Requesster User</th>
        <th>Actions</th>
    </tr>
    <%
        List<Task> tasks = (List<Task>) request.getAttribute("tasks");
        for (Task task : tasks) {
    %>


        <c:forEach var="request" items="${pendingRequests}">
    <tr>
        <td>${request.task.name}</td>
        <td>${request.requester.firstName} ${request.requester.lastName}</td>
        <td>
            <form action="${pageContext.request.contextPath}/approveRequest" method="post">
                <input type="hidden" name="requestId" value="${request.id}">
                <select name="newUserId">
                    <c:forEach var="user" items="${allUsers}">
                        <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                    </c:forEach>
                </select>
                <button type="submit">Approuver</button>
            </form>
        </td>
    </tr>
    </c:forEach>



    <%
        }
    %>

</table>

</body>
</html>


