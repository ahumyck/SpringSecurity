package com.ctf.jwtjku;

import com.ctf.jwtjku.services.PasswordService;
import com.ctf.jwtjku.services.RoleService;
import com.ctf.jwtjku.services.SecureRandomPasswordGenerator;
import com.ctf.jwtjku.services.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories
public class SecuringWebApplication {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecureRandomPasswordGenerator passwordGenerator;

    @Autowired
    private PasswordService passwordService;

    private static final int PASSWORD_LENGTH = 256;

    public static final String USER_ROLE_NAME = "USER";
    public static final String ADMIN_ROLE_NAME = "ADMIN";


    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(SecuringWebApplication.class, args);
    }


    @PostConstruct
    public void init() {
        roleService.createRole(USER_ROLE_NAME);
        roleService.createRole(ADMIN_ROLE_NAME);
        String adminPassword = passwordGenerator.generatePasswordAndGetAsBase64(PASSWORD_LENGTH);
        passwordService.storePassword(PasswordService.ADMIN_PASS_KEY, adminPassword);
        userService.createAdmin("Admin", adminPassword);
    }

}
