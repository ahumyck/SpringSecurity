package com.example.securingweb.controllers;

import com.example.securingweb.entites.User;
import com.example.securingweb.request.AuthorizationRequestBody;
import com.example.securingweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
public class UserAuthenticationController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/sign-up")
	public Optional<User> singUp(HttpServletResponse response, @RequestBody AuthorizationRequestBody body) throws IOException {
		User user = userService.createUser(body);
		if (user == null) {
			response.sendError(400, "Cannot create user");
			return Optional.empty();
		}
		return Optional.of(user);
	}

	@PostMapping(value = "/sign-in")
	public Optional<User> singIn(HttpServletResponse response, @RequestBody AuthorizationRequestBody body) throws IOException {
		User user = userService.findByUsername(body.getUsername());
		if (user == null) {
			response.sendError(400, "Cannot find user");
			return Optional.empty();
		}
		return Optional.of(user);
	}

}
