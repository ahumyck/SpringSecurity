package com.example.securingweb.security.jwt;

import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.userdetails.CustomUserDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JavaWebTokenFilter extends OncePerRequestFilter {

    private final JavaWebTokenService javaWebTokenService;

    private final CustomUserDetailService customUserDetailService;

    private final CookieService cookieService;

    public JavaWebTokenFilter(JavaWebTokenService javaWebTokenService, CustomUserDetailService customUserDetailService, CookieService cookieService) {
        this.javaWebTokenService = javaWebTokenService;
        this.customUserDetailService = customUserDetailService;
        this.cookieService = cookieService;
    }


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
