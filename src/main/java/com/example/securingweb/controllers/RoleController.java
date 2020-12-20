package com.example.securingweb.controllers;

import com.example.securingweb.entites.Role;
import com.example.securingweb.entites.User;
import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.jwt.JavaWebTokenService;
import com.example.securingweb.security.userdetails.CustomUserDetails;
import com.example.securingweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
public class RoleController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaWebTokenService javaWebTokenService;

    @Autowired
    private CookieService cookieService;

    @GetMapping(value = "/user/role")
    public Optional<Role> getUserRole(HttpServletResponse response) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user == null) {
            response.sendError(400, "No cookie");
            return Optional.empty();
        }
        return Optional.of(user.getRole());

    }
}
