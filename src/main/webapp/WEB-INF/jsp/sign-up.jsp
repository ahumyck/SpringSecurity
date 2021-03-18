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
  <form class="login-form" method="POST" action="/sign-up">
    ${errorMessage}
    <div class="user-box">
      <input type="text" name="username" required class="form-input" id="username"  placeholder=" "/>
      <label for="username">Username</label>
    </div>
    <div class="user-box">
      <input type="password"
             name="password"
             required
             class="form-input"
             id="password"
             pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
             placeholder=" "
      />
      <label>Password</label>
    </div>
    <div class="user-box">
      <input type="password" name="repassword" required class="form-input" id="repassword"  placeholder=" ">
      <label>Repeat password</label>
    </div>
    <div class="submit-button">
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    <input type="submit" value="Sing Up"/>
    </div>
  </form>
</div>
<script type="text/javascript">
  var password = document.getElementById("password")
  var confirm_password = document.getElementById("repassword");

  password.addEventListener("input", function (event) {

     if(password.value != confirm_password.value) {
           confirm_password.setCustomValidity("Passwords don't match");
     } else {
           confirm_password.setCustomValidity('');
     }
     if (password.validity.patternMismatch ) {
           password.setCustomValidity("Password must contain at least 8 characters, including UPPER/lowercase and numbers");
     } else {
           password.setCustomValidity("");
     }
  }, false);

  confirm_password.addEventListener("input", function (event) {
     if(password.value != confirm_password.value) {
                confirm_password.setCustomValidity("Passwords don't match");
          } else {
                confirm_password.setCustomValidity('');
          }
    }, false);

    password.addEventListener("change", function (event) {
         if (password.validity.patternMismatch ) {
               password.setCustomValidity("Password must contain at least 8 characters, including UPPER/lowercase and numbers");
         } else {
               password.setCustomValidity("");
         }
      }, false);

      confirm_password.addEventListener("change", function (event) {
         if(password.value != confirm_password.value) {
                    confirm_password.setCustomValidity("Passwords don't match");
              } else {
                    confirm_password.setCustomValidity('');
              }
        }, false);

  </script>
</body>
</html>
