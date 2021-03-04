package com.example.securingweb.controllers;

import com.example.securingweb.entites.Role;
import com.example.securingweb.entites.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RoleController {

    @GetMapping(value = "/user/role")
    public Optional<Role> getUserRole(@AuthenticationPrincipal User user) {
        return Optional.of(user.getRole());
    }
}
