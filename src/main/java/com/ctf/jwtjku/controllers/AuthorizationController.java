package com.ctf.jwtjku.controllers;

import com.ctf.jwtjku.dto.request.UsernamePasswordRequestBody;
import com.ctf.jwtjku.model.entites.User;
import com.ctf.jwtjku.security.cookie.CookieService;
import com.ctf.jwtjku.security.jwt.JsonWebToken;
import com.ctf.jwtjku.security.jwt.JsonWebTokenService;
import com.ctf.jwtjku.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public String singUp(HttpServletResponse response, UsernamePasswordRequestBody body) throws IOException {
        log.info("sign-up request with body:" + body);
        User user = userService.createUser(body);
        if (user == null) {
            response.sendError(400, "Cannot create user");
        }
        return "redirect:/";
    }


    @PostMapping(value = "/sign-in")
    public String singIn(HttpServletResponse response, UsernamePasswordRequestBody body) throws IOException {
        User user = userService.findByUsername(body.getUsername());
        if (user == null) {
            log.info("Cannot find user with " + body.getUsername());
            response.sendError(400, "Cannot find user");
            return "sign-in";
        }
        JsonWebToken jsonWebToken = jsonWebTokenService.generateToken(user.getUsername());
        response.addCookie(cookieService.createTokenCookie(jsonWebToken));
        return "redirect:/";
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
