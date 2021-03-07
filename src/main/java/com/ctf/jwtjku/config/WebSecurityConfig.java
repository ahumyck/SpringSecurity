package com.ctf.jwtjku.config;

import com.ctf.jwtjku.security.jwt.filters.JsonWebTokenFilter;
import com.ctf.jwtjku.security.userdetails.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ctf.jwtjku.SecuringWebApplication.ADMIN_ROLE_NAME;
import static com.ctf.jwtjku.SecuringWebApplication.USER_ROLE_NAME;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService userDetailService;
    private final JsonWebTokenFilter jsonWebTokenFilter;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().
                and().
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                authorizeRequests().
                antMatchers("/admin/*").hasAuthority(ADMIN_ROLE_NAME).
                antMatchers("/user/*").hasAnyAuthority(USER_ROLE_NAME, ADMIN_ROLE_NAME).
                antMatchers("/sign-up", "/sign-in", "/secret", "/vzlomjopi").permitAll().
                and().
                addFilterBefore(jsonWebTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
