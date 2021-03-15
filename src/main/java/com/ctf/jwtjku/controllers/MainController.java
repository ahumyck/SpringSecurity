package com.ctf.jwtjku.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    // внедряем значение из application.properties
    private String message = "Hello World";


    @GetMapping("/")
    public String welcome(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }
}