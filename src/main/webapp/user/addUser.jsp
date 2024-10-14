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

  <form action="users" method="post" class="my-6">
    <button type="submit"
            class="px-4 py-2 my-2 bg-orange rounded text-white hover:bg-primary-100 focus:outline-none transition-colors">
      Add a User
    </button>

    <div class="overflow-x-auto dark:border-gray-700 bg-gray-50 dark:text-gray-400 dark:bg-gray-800">
      <table class="w-full whitespace-no-wrap">
        <thead>
        <tr class="text-xs font-semibold tracking-wide text-left text-gray-500 uppercase border-b dark:border-gray-700 bg-gray-50 dark:text-gray-400 dark:bg-gray-800">
          <th class="px-4 py-3">First name</th>
          <th class="px-4 py-3">Last name</th>
          <th class="px-4 py-3">username</th>
          <th class="px-4 py-3">Email</th>
          <th class="px-4 py-3">Password</th>
          <th class="px-4 py-3">Is manager</th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y dark:divide-gray-700 dark:bg-gray-800">
        <tr>
          <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
            <input type="text" placeholder="First Name" name="firstName" class="border p-2 rounded w-full" required>
          </td>
          <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
            <input type="text" placeholder="Last Name" name="lastName" class="border p-2 rounded w-full" required>
          </td>
          <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
            <input type="text" placeholder="Username" name="username" class="border p-2 rounded w-full" required>
          </td>
          <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
            <input type="email" placeholder="Email" name="email" class="border p-2 rounded w-full" required>
          </td>
          <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
            <input type="password" placeholder="Password" name="password" class="border p-2 rounded w-full" required>
          </td>
          <td class="border border-gray-300 px-3 py-2 dark:border-gray-700 bg-gray-50">
            <label class="flex items-center space-x-2">
              <input type="checkbox" name="isManager" value="true"
                     class="form-checkbox h-5 w-5 text-orange-600">

              <span class="text-gray-700 dark:text-gray-400">Yes</span>
            </label>
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