package com.ctf.jwtjku.services;

import com.ctf.jwtjku.dto.request.UsernamePasswordRequestBody;
import com.ctf.jwtjku.model.entites.User;
import com.ctf.jwtjku.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static com.ctf.jwtjku.SecuringWebApplication.USER_ROLE_NAME;

@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> createUser(UsernamePasswordRequestBody body) {
        return createUser(body, USER_ROLE_NAME);
    }

    public Optional<User> createUser(UsernamePasswordRequestBody body, String role) {
        String username = body.getUsername();
        String password = body.getPassword();
        return createUser(username, password, role);
    }

    public Optional<User> createUser(String username, String password, String role) {
        if (userRepository.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(roleService.findRole(role));
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    public Optional<User> checkUser(UsernamePasswordRequestBody body) throws AuthenticationException {
        User user = userRepository.findByUsername(body.getUsername());
        if (user != null) {
            String encodedPassword = passwordEncoder.encode(body.getPassword());
            if (user.getPassword().equals(encodedPassword)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
