package com.example.securingweb;

import com.example.securingweb.entites.Role;
import com.example.securingweb.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootApplication
@EnableJpaRepositories
public class SecuringWebApplication {

	@Autowired
	private RoleService roleService;


	public static void main(String[] args) {
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
