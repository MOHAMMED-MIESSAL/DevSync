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

<h1>Users List</h1>

<button type="button" onclick="location.href='${pageContext.request.contextPath}/users?id=new'">Add User</button>

<table id="customers">
  <tr>
    <th>ID</th>
    <th>First Name</th>
    <th>Last Name</th>
    <th>User Name</th>
    <th>Email</th>
    <th>is Manager</th>
    <th>Actions</th>
  </tr>
  <%
    List<User> users = (List<User>) request.getAttribute("users");
    for (User user : users) {
  %>
  <tr>
    <td><%= user.getId() %></td>
    <td><%= user.getFirstName() %></td>
    <td><%= user.getLastName() %></td>
    <td><%= user.getUsername() %></td>
    <td><%= user.getEmail() %></td>
    <td><%= user.getIsManager() %></td>
    <td>

      <a href="${pageContext.request.contextPath}/users?&id=<%= user.getId() %>">
        <button>
          Edit
        </button>
      </a>

      <form action="${pageContext.request.contextPath}/users?id=<%= user.getId() %>" method="post">
        <input type="hidden" name="action" value="delete"/>
        <button type="submit">Delete</button>
      </form>

    </td>
  </tr>
  <%
    }
  %>

</table>

</body>
</html>


