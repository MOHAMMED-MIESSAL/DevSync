<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User</title>
    <link rel="stylesheet" href="./assets/css/tailwind.output.css"/>
</head>
<body class="bg-gray-300 dark:bg-gray-900">

<%@include file="WEB-INF/includes/header.jsp" %>
<%@include file="WEB-INF/includes/sidebar.jsp" %>

<main id="main" class="mt-32 h-screen px-28">
    <h2 class="my-6 text-4xl font-semibold text-center font-poppins tracking-widest text-gray-700 dark:text-gray-200">
        <span class="text-primary-100 dark:text-orange">Edit</span> User
    </h2>

    <form action="users" method="post" class="my-6">
        <input type="hidden" name="action" value="update"/>
        <input type="hidden" name="userId" value="${userToEdit.id}"/>

        <div class="grid grid-cols-2 gap-4">
            <div>
                <label>First Name</label>
                <input type="text" name="firstName" value="${userToEdit.firstName}" class="border p-2 rounded w-full"/>
            </div>
            <div>
                <label>Last Name</label>
                <input type="text" name="lastName" value="${userToEdit.lastName}" class="border p-2 rounded w-full"/>
            </div>
            <div>
                <label>Username</label>
                <input type="text" name="username" value="${userToEdit.username}" class="border p-2 rounded w-full"/>
            </div>
            <div>
                <label>Email</label>
                <input type="email" name="email" value="${userToEdit.email}" class="border p-2 rounded w-full"/>
            </div>
            <div>
                <label>Password</label>
                <input type="password" name="password" value="${userToEdit.password}"
                       class="border p-2 rounded w-full"/>
            </div>
            <div>
                <label>Is Manager</label>
                <input type="checkbox" name="isManager" ${userToEdit.isManager ? 'checked' : ''} class="form-checkbox h-5 w-5 text-orange-600"/>

            </div>
        </div>

        <button type="submit"
                class="px-4 py-2 my-6 bg-orange rounded text-white hover:bg-primary-100 focus:outline-none transition-colors">
            Update User
        </button>
    </form>
</main>

<%@include file="WEB-INF/includes/footer.jsp" %>

<script src="./assets/js/navigation.js"></script>
<script src="./assets/js/navbar.js"></script>
<script src="./assets/js/theme.js"></script>
</body>
</html>
