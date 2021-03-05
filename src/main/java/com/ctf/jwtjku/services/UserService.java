package com.ctf.jwtjku.services;

import com.ctf.jwtjku.model.entites.User;
import com.ctf.jwtjku.model.repository.RoleRepository;
import com.ctf.jwtjku.model.repository.UserRepository;
import com.ctf.jwtjku.dto.request.UsernamePasswordRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ctf.jwtjku.SecuringWebApplication.ADMIN_ROLE_NAME;
import static com.ctf.jwtjku.SecuringWebApplication.USER_ROLE_NAME;

@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public User createAdmin(UsernamePasswordRequestBody body){
		return createUser(body, ADMIN_ROLE_NAME);
	}

	public User createUser(UsernamePasswordRequestBody body) {
		return createUser(body, USER_ROLE_NAME);
	}

	public User createUser(UsernamePasswordRequestBody body, String role) {
		String username = body.getUsername();
		String password = body.getPassword();

		if (userRepository.findByUsername(username) == null) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(password));
			user.setRole(roleRepository.findByRoleName(role));
			return userRepository.save(user);
		}

		return null;
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
