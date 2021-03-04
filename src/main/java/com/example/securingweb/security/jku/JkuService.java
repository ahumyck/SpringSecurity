package com.example.securingweb.security.jku;

import lombok.SneakyThrows;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.springframework.web.client.RestTemplate;

public class JkuService {

    private static final String DEFAULT_JKU = "http://localhost:8080/secret";
    private final RestTemplate restTemplate = new RestTemplate();

    @SneakyThrows
    public JsonWebKeySet getJsonWebKey(String jku) {
        return new JsonWebKeySet(restTemplate.getForObject(jku, String.class));
    }

    public String getDefaultJku() {
        return DEFAULT_JKU;
    }

}
