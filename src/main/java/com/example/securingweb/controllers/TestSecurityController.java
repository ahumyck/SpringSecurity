package com.example.securingweb.controllers;


import com.example.securingweb.entites.User;
import com.example.securingweb.security.userdetails.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TestSecurityController {

    private String userResponse(HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        if (user == null) {
            response.sendError(400, "Seems like you do not have access to this page");
            return "Seems like no access";
        }
        return "Hi, " + user;
    }

    @GetMapping(value = "/admin/hi")
    public String sayHiAdministrator(HttpServletResponse response) throws IOException {
        return userResponse(response);
    }

    @GetMapping(value = "/user/hi")
    public String sayHiUser(HttpServletResponse response) throws IOException {
        return userResponse(response);
    }

}
