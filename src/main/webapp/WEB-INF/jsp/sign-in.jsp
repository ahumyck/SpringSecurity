<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>Sign In</title>
  <spring:url value="/css/style.css" var="springCss" />
  <link href="${springCss}" rel="stylesheet" />


</head>
<body>
<div class="login-box">
  <h2>Login</h2>
  ${errorMessage}
  <form class="login-form" method="post" action="/sign-in">
    <div class="user-box">
      <input type="text" name="username" required class="form-input" id="username" placeholder=" "/>
      <label for="username">Username</label>
    </div>
    <div class="user-box">
      <input type="password" name="password" required class="form-input" id="password" placeholder=" "/>
      <label>Password</label>
    </div>
    <div class="submit-button">
    <span></span>
      <span></span>
      <span></span>
      <span></span>
    <input type="submit" value="Sign In"/>
    </div>
  </form>
</div>
</body>
</html>

