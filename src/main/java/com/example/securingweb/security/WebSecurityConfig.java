package com.example.securingweb.security;

import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.cookie.encryption.AESCookieEncryptionService;
import com.example.securingweb.security.cookie.encryption.CookieEncryptionService;
import com.example.securingweb.security.jwt.JavaWebTokenFilter;
import com.example.securingweb.security.jwt.JavaWebTokenService;
import com.example.securingweb.security.userdetails.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.securingweb.SecuringWebApplication.ADMIN_ROLE_NAME;
import static com.example.securingweb.SecuringWebApplication.USER_ROLE_NAME;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JavaWebTokenFilter javaWebTokenFilter;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

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
                antMatchers("/admin/*").hasAuthority(ADMIN_ROLE_NAME).
                antMatchers("/user/*").hasAuthority(USER_ROLE_NAME).
                antMatchers("/user-role").hasAnyAuthority(USER_ROLE_NAME, ADMIN_ROLE_NAME).
                antMatchers("/sign-up", "/sign-in").permitAll().
                and().addFilterBefore(javaWebTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JavaWebTokenService javaWebTokenService() {
        return new JavaWebTokenService();
    }

    @Bean
    public CookieEncryptionService defaultCookieEncryptionService() {
        return new AESCookieEncryptionService();
    }

    @Bean
    public CookieService cookieService() {
        return new CookieService(defaultCookieEncryptionService());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
