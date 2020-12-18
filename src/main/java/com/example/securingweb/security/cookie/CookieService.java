package com.example.securingweb.security.cookie;

import com.example.securingweb.security.jwt.JavaWebToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Service("cookieService")
@Slf4j
public class CookieService {


    @Value("${token.name}")
    private String cookieName;

    public Cookie createTokenCookie(String token, int duration) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setMaxAge(duration);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie createTokenCookie(JavaWebToken javaWebToken) {
        return createTokenCookie(javaWebToken.getValue(), javaWebToken.getDuration());
    }

    public Cookie deleteCookie(String token) {
        return createTokenCookie(token, 0);
    }

    public Optional<String> getCookieFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return Optional.empty();
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                String token = cookie.getValue();
                if (token == null) return Optional.empty();
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }
}
