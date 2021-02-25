package com.example.securingweb.controllers;

import com.example.securingweb.entites.User;
import com.example.securingweb.request.UsernamePasswordRequestBody;
import com.example.securingweb.security.jwt.JsonWebToken;
import com.example.securingweb.security.jwt.JsonWebTokenService;
import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.securingweb.security.jwt.JsonWebTokenService.JWT_SECRET;

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

    //todo just for test, need impl in future
    @GetMapping(value = "/secret")
    public String secret() throws IOException {
        return JWT_SECRET;
    }

    //todo just for test, need remove in future
    @GetMapping(value = "/vzlomjopi")
    public String vzlom() throws IOException {
        return "c2VjcmV0";// base64encoded
    }
}
