package com.ctf.jwtjku;

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

    private static final int PASSWORD_LENGTH = 1024;

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
        userService.createAdmin("ADMIN", passwordGenerator.generatePasswordAndGetAsBase64(PASSWORD_LENGTH));
    }

}
