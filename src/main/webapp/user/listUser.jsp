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

  <h2 class="my-6 text-4xl font-semibold text-center font-poppins tracking-widest text-gray-700 dark:text-gray-200">
    <span class="text-primary-100 dark:text-orange">List</span> Users
  </h2>

  <a href="${pageContext.request.contextPath}/users?id=new">
    <button class="px-4 py-2 my-2 bg-orange rounded text-white hover:bg-primary-100 focus:outline-none transition-colors">
      Add New User
    </button>
  </a>



  <div class="w-full overflow-hidden rounded-lg shadow-xs">
    <div class="w-full overflow-x-auto">
      <table class="w-full whitespace-no-wrap">
        <thead>
        <tr class="text-xs font-semibold tracking-wide text-left text-gray-500 uppercase border-b dark:border-gray-700 bg-gray-50 dark:text-gray-400 dark:bg-gray-800">
          <th class="px-4 py-3">full name</th>
          <th class="px-4 py-3">username</th>
          <th class="px-4 py-3">Email</th>
          <th class="px-4 py-3">Is manager</th>
          <th class="px-4 py-3">Actions</th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y dark:divide-gray-700 dark:bg-gray-800">
        <c:forEach var="user" items="${users}">
          <tr class="text-gray-700 dark:text-gray-400">
            <td class="px-4 py-3">
              <div class="flex items-center text-sm">
                <div class="relative hidden w-8 h-8 mr-3 rounded-full md:block">
                  <div class="absolute inset-0 rounded-full shadow-inner" aria-hidden="true"></div>
                </div>
                <div>
                  <p class="text-xs text-gray-600 dark:text-gray-400">
                      ${user.firstName}
                  </p>
                  <p class="text-xs text-gray-600 dark:text-gray-400">
                      ${user.lastName}
                  </p>
                </div>
              </div>
            </td>
            <td class="px-4 py-3 text-sm">
                ${user.username}
            </td>
            <td class="px-4 py-3 text-sm">
                ${user.email}
            </td>
            <td class="px-4 py-3 text-xs">
                <span class="px-2 py-1 font-semibold leading-tight text-green-700 bg-green-100 rounded-full dark:bg-green-700 dark:text-green-100">
                    ${user.isManager}
                </span>
            </td>
            <td class="px-4 py-3">
              <div class="flex items-center space-x-4 text-sm">
                <!-- Edit button -->
                <a href="users?id=${user.id}">
                  <button class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-purple-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray"
                          aria-label="Edit">
                    <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
                      <path d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z"></path>
                    </svg>
                  </button>
                </a>

                <!-- Delete button -->
                <form action="users" method="post">
                  <input type="hidden" name="action" value="delete"/>
                  <input type="hidden" name="userId" value="${user.id}"/>
                  <button class="flex items-center justify-between px-2 py-2 text-sm font-medium leading-5 text-red-600 rounded-lg dark:text-gray-400 focus:outline-none focus:shadow-outline-gray"
                          aria-label="Delete">
                    <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
                      <path fill-rule="evenodd"
                            d="M8.707 9.707a1 1 0 01-1.414 0L5 7.414 3.707 8.707a1 1 0 01-1.414-1.414l1.5-1.5a1 1 0 011.414 0L8 7.414l5-5A1 1 0 1113.707 4.293l-5 5z"
                            clip-rule="evenodd"></path>
                    </svg>
                  </button>
                </form>
              </div>
            </td>

          </tr>
        </c:forEach>
        </tbody>

      </table>
    </div>

  </div>
</main>

<%@include file="../WEB-INF/includes/footer.jsp" %>

<script src="assets/js/navigation.js"></script>
<script src="assets/js/navbar.js"></script>
<script src="assets/js/theme.js"></script>
</body>
</html>