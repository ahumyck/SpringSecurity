package com.example.securingweb.security.jwt;

import com.example.securingweb.security.userdetails.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;


@Component
@Slf4j
public class JavaWebTokenFilter extends GenericFilterBean {

    @Autowired
    private JavaWebTokenService javaWebTokenService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    public static final String AUTHORIZATION = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional<String> token = getCookieFromRequest((HttpServletRequest) request);

        token.ifPresent(javaWebToken -> {
            String username = javaWebTokenService.validateTokenAndGetUsername(javaWebToken);
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);

        });

        chain.doFilter(request, response);
    }

    private Optional<String> getCookieFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return Optional.of(bearer.substring(7));
        }
        return Optional.empty();
    }

}
