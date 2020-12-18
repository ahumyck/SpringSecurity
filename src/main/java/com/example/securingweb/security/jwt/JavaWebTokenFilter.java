package com.example.securingweb.security.jwt;

import com.example.securingweb.security.userdetails.CustomUserDetailService;
import com.example.securingweb.services.CookieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@Component
@Slf4j
public class JavaWebTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JavaWebTokenService javaWebTokenService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CookieService cookieService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = cookieService.getCookieFromRequest(request);

        token.ifPresent(javaWebToken -> {
            String username = javaWebTokenService.validateTokenAndGetUsername(javaWebToken);
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
        });

        filterChain.doFilter(request, response);
    }
}
