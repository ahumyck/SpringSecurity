<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>
  <title>Главная</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="login-box">
  <sec:authorize access="isAuthenticated()">
    <h2>Hello, ${pageContext.request.userPrincipal.name}</h2>
    <div class="submit-button">
      ${message}
    </div>
    <div class="submit-button">
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <a href="/logout">Logout</a>
    </div>
  </sec:authorize>
</div>
</body>
</html>