package com.example.securingweb;

import com.example.securingweb.entites.Role;
import com.example.securingweb.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(SecuringWebApplication.class);

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(SecuringWebApplication.class, args);
	}

	@PostConstruct
	public void init() {
		roleService.createRole("USER");
		roleService.createRole("ADMIN");

		for (Role role : roleService.getRoles()) {
			System.out.println(role);
		}

	}

}
