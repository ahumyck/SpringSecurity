package com.example.securingweb.services;

import com.example.securingweb.entites.Role;
import com.example.securingweb.entites.User;
import com.example.securingweb.repository.RoleRepository;
import com.example.securingweb.repository.UserRepository;
import com.example.securingweb.request.AuthorizationRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final RoleRepository roleRepository;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User saveUser(User user) {
		Role role = roleRepository.findByRoleName("USER");
		user.setRole(role);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User createUser(AuthorizationRequestBody body) {
		String username = body.getUsername();
		String password = body.getPassword();

		if (userRepository.findByUsername(username) == null) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(password));
			user.setRole(roleRepository.findByRoleName("USER"));
			return userRepository.save(user);
		}

		return null;
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
