package com.ctf.jwtjku.config;

import com.ctf.jwtjku.model.repository.RoleRepository;
import com.ctf.jwtjku.model.repository.UserRepository;
import com.ctf.jwtjku.security.cookie.CookieService;
import com.ctf.jwtjku.security.cookie.encryption.CookieEncryptionService;
import com.ctf.jwtjku.security.cookie.encryption.DefaultCookieEncryptionService;
import com.ctf.jwtjku.security.jwt.JsonWebTokenService;
import com.ctf.jwtjku.security.jwt.SecureJsonWebTokenService;
import com.ctf.jwtjku.security.jwt.filters.JsonWebTokenFilter;
import com.ctf.jwtjku.security.jwt.jku.JkuService;
import com.ctf.jwtjku.security.userdetails.CustomUserDetailService;
import com.ctf.jwtjku.services.PasswordService;
import com.ctf.jwtjku.services.RoleService;
import com.ctf.jwtjku.services.SecureRandomPasswordGenerator;
import com.ctf.jwtjku.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfiguration {

    @Bean
    public UserDetailsService userDetailService(UserRepository userRepository) {
        return new CustomUserDetailService(userRepository);
    }

    @Bean
    public RoleService roleService(RoleRepository roleRepository) {
        return new RoleService(roleRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        return new UserService(userRepository, roleService, passwordEncoder);
    }

    @Bean
    public JsonWebTokenFilter jsonWebTokenFilter(JsonWebTokenService jsonWebTokenService, UserDetailsService userDetailService) {
        return new JsonWebTokenFilter(jsonWebTokenService, userDetailService, cookieService());
    }

    @Bean
    public JkuService jkuService() {
        return new JkuService();
    }

    @Bean
    public SecureJsonWebTokenService jsonWebTokenService(JkuService jkuService) {
        return new SecureJsonWebTokenService(jkuService);
    }

    @Bean
    public CookieEncryptionService cookieEncryptionService() {
        return new DefaultCookieEncryptionService();
    }

    @Bean
    public CookieService cookieService() {
        return new CookieService(cookieEncryptionService());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecureRandomPasswordGenerator passwordGenerator() {
        return new SecureRandomPasswordGenerator();
    }

    @Bean
    public PasswordService passwordKeeper(SecureRandomPasswordGenerator passwordGenerator) {
        return new PasswordService(passwordGenerator);
    }
}
