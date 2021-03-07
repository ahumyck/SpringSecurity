package com.ctf.jwtjku.services;

import com.ctf.jwtjku.dto.request.UsernamePasswordRequestBody;
import com.ctf.jwtjku.model.entites.User;
import com.ctf.jwtjku.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ctf.jwtjku.SecuringWebApplication.ADMIN_ROLE_NAME;
import static com.ctf.jwtjku.SecuringWebApplication.USER_ROLE_NAME;

@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public User createAdmin(UsernamePasswordRequestBody body) {
        return createUser(body, ADMIN_ROLE_NAME);
    }

    public User createAdmin(String adminName, String adminPassword) {
        return createUser(adminName, adminPassword, ADMIN_ROLE_NAME);
    }

    public User createUser(UsernamePasswordRequestBody body) {
        return createUser(body, USER_ROLE_NAME);
    }

    public User createUser(UsernamePasswordRequestBody body, String role) {
        String username = body.getUsername();
        String password = body.getPassword();
        return createUser(username, password, role);
    }

    public User createUser(String username, String password, String role) {
        if (userRepository.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(roleService.findRole(role));
            return userRepository.save(user);
        }
        return null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
