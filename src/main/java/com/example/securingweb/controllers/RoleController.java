package com.example.securingweb.controllers;

import com.example.securingweb.entites.Role;
import com.example.securingweb.entites.User;
import com.example.securingweb.request.TokenRequestBody;
import com.example.securingweb.security.jwt.JavaWebTokenService;
import com.example.securingweb.services.RoleService;
import com.example.securingweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private JavaWebTokenService javaWebTokenService;

	@GetMapping(value = "/user-role")
	public Optional<Role> getUserRole(HttpServletResponse response, @RequestBody TokenRequestBody body) throws IOException {
		try {
			User user = userService.findByUsername(javaWebTokenService.validateTokenAndGetUsername(body.getTokenValue()));
			if (user == null) {
				response.sendError(400, "Cannot find user");
				return Optional.empty();
			}
			return Optional.of(user.getRole());
		}
		catch (Exception e) {
			response.sendError(400, e.getMessage());
			return Optional.empty();
		}
	}

	@GetMapping(value = "/roles")
	public List<Role> getRoles() {
		return roleService.getRoles();
	}
}
