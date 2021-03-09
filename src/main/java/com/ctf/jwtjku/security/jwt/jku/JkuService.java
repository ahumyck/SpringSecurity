package com.ctf.jwtjku.security.jwt.jku;

import com.ctf.jwtjku.services.PasswordService;
import lombok.SneakyThrows;
import org.jose4j.http.SimpleGet;
import org.jose4j.http.SimpleResponse;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JkuService {

    public static final String SECRET_PASSWORD_KEY = "secretPassword";
    private static final String DEFAULT_JKU = "http://localhost:8080/secret";
    private final RestTemplate restTemplate = new RestTemplate();

    @SneakyThrows
    public JsonWebKey getJsonWebKey(String jku) {
        return JsonWebKey.Factory.newJwk(restTemplate.getForObject(jku, String.class));
    }

    public String getDefaultJku() {
        return DEFAULT_JKU;
    }

}
