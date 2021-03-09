package com.ctf.jwtjku.security.jwt.jku;

import lombok.SneakyThrows;
import org.jose4j.http.Get;
import org.jose4j.http.Response;
import org.jose4j.http.SimpleResponse;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomSimpleGet extends Get {
    @SneakyThrows
    @Override
    public SimpleResponse get(String location) {
        SimpleResponse sp = super.get(location);
        String body = sp.getBody();
        JsonWebKey jsonWebKey = JsonWebKey.Factory.newJwk(body);
        JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(jsonWebKey);
        Map<String, List<String>> headers = sp.getHeaderNames()
                .stream()
                .collect(Collectors.toMap(name -> name, sp::getHeaderValues));
        return new Response(sp.getStatusCode(), sp.getStatusMessage(), headers, jsonWebKeySet.toJson());
    }
}
