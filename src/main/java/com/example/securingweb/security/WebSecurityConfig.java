package com.example.securingweb.security;

import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.cookie.encryption.CookieEncryptionService;
import com.example.securingweb.security.cookie.encryption.DefaultCookieEncryptionService;
import com.example.securingweb.security.filters.CsrfTokenFilter;
import com.example.securingweb.security.filters.JsonWebTokenFilter;
import com.example.securingweb.security.jku.JkuService;
import com.example.securingweb.security.jwt.JsonWebTokenService;
import com.example.securingweb.security.jwt.JsonWebTokenServiceImpl;
import com.example.securingweb.security.jwt.SecureJsonWebTokenService;
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
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import static com.example.securingweb.SecuringWebApplication.ADMIN_ROLE_NAME;
import static com.example.securingweb.SecuringWebApplication.USER_ROLE_NAME;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JsonWebTokenFilter jsonWebTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                httpBasic().
                and().
//                addFilterBefore(csrfTokenFilter(), CsrfFilter.class).
//                csrf().csrfTokenRepository(csrfTokenRepository()).
        csrf().disable().
//                and().
        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                authorizeRequests().
                antMatchers("/admin/*").hasAuthority(ADMIN_ROLE_NAME).
                antMatchers("/user/*").hasAnyAuthority(USER_ROLE_NAME, ADMIN_ROLE_NAME).
                antMatchers("/sign-up", "/sign-in", "/secret", "/vzlomjopi").permitAll().
                and().
                addFilterBefore(jsonWebTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public CsrfTokenFilter csrfTokenFilter() {
        return new CsrfTokenFilter();
    }

    @Bean
    public JsonWebTokenFilter jsonWebTokenFilter(JsonWebTokenService jsonWebTokenService) {
        return new JsonWebTokenFilter(jsonWebTokenService, userDetailService, cookieService());
    }

    @Bean
    public JkuService jkuService(){
        return new JkuService();
    }

    @Bean
    public JsonWebTokenService jsonWebTokenService(JkuService jkuService) {
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
}
