<%-- erreur.jsp --%>
<%@ page isErrorPage="true" %>
<html>
<body>
exception levée <b><%= exception %>
</b>
<hr>
<h3>trace de la pile</h3>
<pre>
      <%
          java.io.PrintWriter pw = new java.io.PrintWriter(out);
          exception.printStackTrace(pw);
      %>
    </pre>
</body>
</html>