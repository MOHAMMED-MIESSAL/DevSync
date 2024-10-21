<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="path/to/your/css/style.css"> <!-- Ajoutez votre CSS si nécessaire -->
</head>
<body>
<div class="error-container">
    <h1>An Error Occurred</h1>

    <!-- Affichage du message d'erreur passé depuis le Servlet -->
    <p style="color: red;">
        <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "An unexpected error occurred." %>
    </p>

    <p>
        <a href="javascript:history.back()">Go Back</a> <!-- Lien pour revenir à la page précédente -->
    </p>
</div>
</body>
</html>
