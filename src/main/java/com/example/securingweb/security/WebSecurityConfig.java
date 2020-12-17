package com.example.securingweb.security;

import com.example.securingweb.security.jwt.JavaWebTokenFilter;
import com.example.securingweb.security.jwt.JavaWebTokenService;
import com.example.securingweb.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JavaWebTokenFilter javaWebTokenFilter;

	@Autowired
	private RoleService roleService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
				httpBasic().
				disable().
				csrf().
				disable().
				sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
				and().
				authorizeRequests().
				antMatchers("/admin/*").hasRole("ADMIN").
				antMatchers("/user-role").hasRole("USER").
				antMatchers("/user/*").hasAnyRole(String.valueOf(roleService.getRoles())).
				antMatchers("/sign-up", "/sign-in").permitAll().
				and().addFilterBefore(javaWebTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
