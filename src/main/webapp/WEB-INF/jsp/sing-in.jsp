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
  <form class="login-form" method="post" action="/sing-in">
    <div class="user-box">
      <input type="text" name="username" required="" class="form-input" id="username"/>
      <label for="username">Username</label>
    </div>
    <div class="user-box">
      <input type="password" name="password" required="" class="form-input" id="password">
      <label>Password</label>
    </div>
    <div class="submit-button">
    <span></span>
      <span></span>
      <span></span>
      <span></span>
    <input type="submit" value="Submit"/>
    </div>
  </form>
</div>
</body>
</html>

