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
  <form action="users" method="post" >
    <tbody>
  <tr>
    <td >
      <input type="text" placeholder="First Name" name="firstName"  required>
    </td>
    <td >
      <input type="text" placeholder="Last Name" name="lastName" required>
    </td>
    <td >
      <input type="text" placeholder="Username" name="username"  required>
    </td>
    <td >
      <input type="email" placeholder="Email" name="email"  required>
    </td>
    <td >
      <input type="password" placeholder="Password" name="password"  required>
    </td>
    <td >
      <label >
        <input type="checkbox" name="isManager" value="true">
        <span >Yes</span>
      </label>
    </td>

  </tr>
  </tbody>
    <button type="submit" >Add User</button>
    </form>

</table>

</body>
</html>