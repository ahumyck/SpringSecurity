package com.ctf.jwtjku.controllers;

import com.ctf.jwtjku.security.cookie.CookieService;
import com.ctf.jwtjku.security.jwt.JsonWebToken;
import com.ctf.jwtjku.services.UserService;
import com.ctf.jwtjku.model.entites.User;
import com.ctf.jwtjku.dto.request.UsernamePasswordRequestBody;
import com.ctf.jwtjku.security.jwt.JsonWebTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @Autowired
    private JsonWebTokenService jsonWebTokenService;

    @Autowired
    private CookieService cookieService;

    @PostMapping(value = "/sign-up")
    public String singUp(HttpServletResponse response, @RequestBody UsernamePasswordRequestBody body) throws IOException {
        User user = userService.createUser(body);
        if (user == null) {
            response.sendError(400, "Cannot create user");
        }
        return "Username " + body.getUsername() + " was signed-up";
    }

    //todo remove
    @PostMapping(value = "/sign-up-admin")
    public String singUpAdmin(HttpServletResponse response, @RequestBody UsernamePasswordRequestBody body) throws IOException {
        User user = userService.createAdmin(body);
        if (user == null) {
            response.sendError(400, "Cannot create user");
        }
        return "Admin " + body.getUsername() + " was signed-up";
    }


    @PostMapping(value = "/sign-in")
    public String singIn(HttpServletResponse response, @RequestBody UsernamePasswordRequestBody body) throws IOException {
        User user = userService.findByUsername(body.getUsername());
        if (user == null) {
            response.sendError(400, "Cannot find user");
            return "Cannot authorize username " + body.getUsername();
        }
        JsonWebToken jsonWebToken = jsonWebTokenService.generateToken(user.getUsername());
        response.addCookie(cookieService.createTokenCookie(jsonWebToken));
        return "Authorization successful, check your cookie";
    }


}
