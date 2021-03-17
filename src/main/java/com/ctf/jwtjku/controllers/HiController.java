package com.ctf.jwtjku.controllers;


import com.ctf.jwtjku.model.entites.User;
import com.ctf.jwtjku.security.userdetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ctf.jwtjku.SecuringWebApplication.ADMIN_ROLE_NAME;

@Controller
public class HiController {

    @Value("${ctf.flag}")
    private String CTF_FLAG;

    @GetMapping(value = "/say-hi")
    public String sayHi(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        if (user.getRole().getRoleName().equals(ADMIN_ROLE_NAME))
            model.addAttribute("message", "Flag is here " + CTF_FLAG);
        else
            model.addAttribute("message", "You are simple user.");
        return "say-hi";
    }
}
