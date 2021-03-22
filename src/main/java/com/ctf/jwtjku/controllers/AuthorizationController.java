package com.ctf.jwtjku.controllers;

import com.ctf.jwtjku.dto.request.UsernamePasswordRequestBody;
import com.ctf.jwtjku.model.exception.CreateUserException;
import com.ctf.jwtjku.model.entites.User;
import com.ctf.jwtjku.security.cookie.CookieService;
import com.ctf.jwtjku.security.jwt.JsonWebToken;
import com.ctf.jwtjku.security.jwt.JsonWebTokenService;
import com.ctf.jwtjku.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @Autowired
    private JsonWebTokenService jsonWebTokenService;

    @Autowired
    private CookieService cookieService;

    @PostMapping(value = "/sign-up")
    public String singUp(HttpServletResponse response, UsernamePasswordRequestBody body, ModelMap model) {
        try {
            // log.info("sign-up request with body:" + body); may produce too much output
            userService.createUser(body).orElseThrow(CreateUserException::new);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "<div class=\"error-message\">User with such name already exists</div>");
            return "sign-up";
        }
    }


    @PostMapping(value = "/sign-in")
    public String singIn(HttpServletResponse response, UsernamePasswordRequestBody body, ModelMap model) {
        try {
            User user = userService.checkUser(body).orElseThrow(AuthenticationException::new);
            JsonWebToken jsonWebToken = jsonWebTokenService.generateToken(user.getUsername());
            response.addCookie(cookieService.createTokenCookie(jsonWebToken));
            return "redirect:/";
        } catch (AuthenticationException e) {
            // log.info("Cannot find user with " + body.getUsername()); may produce too much output
            model.addAttribute("errorMessage", "<div class=\"error-message\">Wrong login or password</div>");
            return "sign-in";
        }
    }

    @GetMapping("/sign-in")
    public String login() {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String singUp() {
        return "sign-up";
    }
}
