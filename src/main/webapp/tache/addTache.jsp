<%@ page import="com.devsync.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management</title>
    <link rel="stylesheet" href="assets/css/tailwind.output.css"/>

</head>
<body class="bg-gray-300 dark:bg-gray-900">

<%@include file="../WEB-INF/includes/header.jsp" %>

<%@include file="../WEB-INF/includes/sidebar.jsp" %>

<main id="main" class="mt-32 h-screen px-28">

    <% if (session.getAttribute("loggedUser") != null) { %>
    <%= session.getAttribute("loggedUser") %>
    <% } else { %>
    User not found.
    <% } %>


    <form action="taches" method="post" class="my-6">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="creatorId" value="<%= ((User)session.getAttribute("loggedUser")).getId() %>">
        <button type="submit"
                class="px-4 py-2 my-2 bg-orange rounded text-white hover:bg-primary-100 focus:outline-none transition-colors">
            Add a Task
        </button>

        <div class="overflow-x-auto dark:border-gray-700 bg-gray-50 dark:text-gray-400 dark:bg-gray-800">
            <table class="w-full whitespace-no-wrap">
                <thead>
                <tr class="text-xs font-semibold tracking-wide text-left text-gray-500 uppercase border-b dark:border-gray-700 bg-gray-50 dark:text-gray-400 dark:bg-gray-800">
                    <th class="px-4 py-3">Name</th>
                    <th class="px-4 py-3">Status</th>
                    <th class="px-4 py-3">Date Creation</th>
                    <th class="px-4 py-3">Utilisateur Affecté</th>
                </tr>
                </thead>
                <tbody class="bg-white divide-y dark:divide-gray-700 dark:bg-gray-800">
                <tr>
                    <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
                        <input type="text" placeholder="Name" name="name" class="border p-2 rounded w-full" required>
                    </td>
                    <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
                        <select name="status" class="border p-2 rounded w-full" required>
                            <option value="">Select Status</option>
                            <option value="NOT_STARTED">Not Started</option>
                            <option value="IN_PROGRESS">In Progress</option>
                            <option value="COMPLETED">Completed</option>
                            <option value="ON_HOLD">On Hold</option>
                        </select>
                    </td>
                    <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
                        <input type="date" name="dateCreation" class="border p-2 rounded w-full" required>
                    </td>
                    <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
                        <select name="utilisateurAffecte" class="border p-2 rounded w-full" required>
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
                    </td>
                    <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
                        <label for="tags">Enter Tags (separated by commas):</label>
                        <input type="text" id="tags" name="tags" required>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </form>




</main>

<%@include file="../WEB-INF/includes/footer.jsp" %>

<script src="assets/js/navigation.js"></script>
<script src="assets/js/navbar.js"></script>
<script src="assets/js/theme.js"></script>
</body>
</html>