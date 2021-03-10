package com.ctf.jwtjku.security.jwt.jku;

import lombok.SneakyThrows;
import org.jose4j.jwk.JsonWebKey;
import org.springframework.web.client.RestTemplate;

public class JkuService {

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
