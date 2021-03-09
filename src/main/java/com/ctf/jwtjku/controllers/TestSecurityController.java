package com.ctf.jwtjku.controllers;


import com.ctf.jwtjku.security.userdetails.CustomUserDetails;
import com.ctf.jwtjku.model.entites.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ctf.jwtjku.SecuringWebApplication.ADMIN_ROLE_NAME;

@RestController
public class TestSecurityController {

    @Value("${ctf.flag}")
    private String CTF_FLAG;

    private String userResponse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        if(user.getRole().getRoleName().equals(ADMIN_ROLE_NAME))
            return "Hi, flag is here " + CTF_FLAG;
        return "Hi, " + user;
    }

    @GetMapping(value = "/hi")
    public String sayHi() {
        return userResponse();
    }
}
