package com.example.securingweb.controllers;

import com.example.securingweb.entites.User;
import com.example.securingweb.request.UsernamePasswordRequestBody;
import com.example.securingweb.response.AuthorizationResponseBody;
import com.example.securingweb.security.jwt.JavaWebTokenService;
import com.example.securingweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthenticationController {

	@Autowired
	private UserService userService;

	@Autowired
	private JavaWebTokenService javaWebTokenService;

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
	public AuthorizationResponseBody singIn(HttpServletResponse response, @RequestBody UsernamePasswordRequestBody body) throws IOException {
		User user = userService.findByUsername(body.getUsername());
		if (user == null) {
			response.sendError(400, "Cannot find user");
		}
		return new AuthorizationResponseBody(javaWebTokenService.generateToken(body.getUsername()).getValue());
	}

}
