package com.example.securingweb.controllers;


import com.example.securingweb.request.UsernamePasswordRequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class TestSecurityController {

	@GetMapping(value = "/admin/hi")
	public String sayHiAdministrator() {
		return "Hi, Admin!";
	}

	@GetMapping(value = "/user/hi")
	public String sayHiUser() {
		return "Hi, User!";
	}

}
