package com.ctf.jwtjku.security.jwt.filters;

import com.ctf.jwtjku.security.cookie.CookieService;
import com.ctf.jwtjku.security.jwt.JsonWebTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class JsonWebTokenFilter extends OncePerRequestFilter {
    private final JsonWebTokenService jsonWebTokenService;
    private final UserDetailsService customUserDetailService;
    private final CookieService cookieService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<String> token = cookieService.getCookieFromRequest(request);
        token.ifPresent(javaWebToken -> {
            try {
                String username = jsonWebTokenService.validateTokenAndGetUsername(javaWebToken);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
            } catch (Exception e) {
                //log.error(e.getMessage()); may produce many too much output
            }

        });
        filterChain.doFilter(request, response);
    }
}
