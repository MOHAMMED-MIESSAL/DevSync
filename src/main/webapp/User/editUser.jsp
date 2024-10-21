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


<table id="customers">
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>User Name</th>
        <th>Email</th>
        <th>Password</th>
        <th>is Manager</th>
    </tr>


    <form action="${pageContext.request.contextPath}/users?id=${userToEdit.id}" method="post" >
        <input type="hidden" name="action" value="update"/>

        <tbody>
        <tr>
            <td >
                <input type="text" placeholder="First Name" value="${userToEdit.firstName}" name="firstName"  required>
            </td>
            <td >
                <input type="text" placeholder="Last Name" value="${userToEdit.lastName}" name="lastName" required>
            </td>
            <td >
                <input type="text" placeholder="Username" value="${userToEdit.username}" name="username"  required>
            </td>
            <td >
                <input type="email" placeholder="Email" value="${userToEdit.email}" name="email"  required>
            </td>
            <td >
                <input type="password" placeholder="Password" value="${userToEdit.password}" name="password"  required>
            </td>
            <td >
                <label >
                    <input type="checkbox" name="isManager" ${userToEdit.isManager ? 'checked' : ''} value="true">
                    <span >Yes</span>
                </label>
            </td>

        </tr>
        </tbody>
        <button type="submit" >Update User</button>
    </form>

</table>

</body>
</html>