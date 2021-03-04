package com.example.securingweb.controllers;


import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.jwt.JsonWebToken;
import com.example.securingweb.security.jwt.JsonWebTokenService;
import com.example.securingweb.security.jwt.SecureJsonWebTokenService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;


@RestController
public class JkuController {
    @Autowired
    private SecureJsonWebTokenService secureJsonWebTokenService;

    @Autowired
    private CookieService cookieService;

    //todo just for test, need impl in future
    @GetMapping(value = "/secret")
    public String secret() {
        return Base64.encode(JsonWebTokenService.SECRET_BYTES);
    }

    //todo just for test, need remove in future
    @GetMapping(value = "/vzlomjopi")
    public String vzlom() {
        return Base64.encode("secret".getBytes(StandardCharsets.UTF_8)); //c2VjcmV0
    }

    @SneakyThrows
    @GetMapping(value = "/getKey")
    public Object webKeyGenerator(HttpServletResponse response) {
        JsonWebToken jsonWebToken = secureJsonWebTokenService.generateToken("username");
        response.addCookie(cookieService.createTokenCookie(jsonWebToken));
        return "Authorization successful, check your cookie";    }
}
