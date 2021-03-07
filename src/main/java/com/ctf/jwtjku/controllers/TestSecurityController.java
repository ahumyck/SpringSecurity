package com.ctf.jwtjku.controllers;


import com.ctf.jwtjku.security.userdetails.CustomUserDetails;
import com.ctf.jwtjku.model.entites.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSecurityController {

    private String response() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        return "Hi, " + user;
    }

    @GetMapping(value = "/admin/hi")
    public String sayHiAdministrator() {
        return response();
    }

    @GetMapping(value = "/user/hi")
    public String sayHiUser() {
        return response();
    }

}
