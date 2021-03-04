package com.example.securingweb.controllers;


import com.example.securingweb.security.cookie.CookieService;
import com.example.securingweb.security.jwt.JsonWebToken;
import com.example.securingweb.security.jwt.SecureJsonWebTokenService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.base64url.Base64Url;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.keys.HmacKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static com.example.securingweb.security.jwt.JsonWebTokenServiceImpl.SECRET_BYTES;

@Slf4j
@RestController
public class JkuController {
    @Autowired
    private SecureJsonWebTokenService secureJsonWebTokenService;

    @Autowired
    private CookieService cookieService;

    //todo just for test, need impl in future
    @GetMapping(value = "/secret")
    public String secret() {
        OctetSequenceJsonWebKey jsonWebKey = new OctetSequenceJsonWebKey(new HmacKey(SECRET_BYTES));
        jsonWebKey.setAlgorithm(AlgorithmIdentifiers.HMAC_SHA512);
        jsonWebKey.setKeyId("xui");
        JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(jsonWebKey);
        log.info("=> jwk: " + jsonWebKey.toJson());
        return jsonWebKeySet.toJson();

    }

    private String produceThumbprintHashInput(OctetSequenceJsonWebKey jsonWebKey) {
        String template = "{\"k\":\"%s\",\"kty\":\"oct\"}";
        byte[] octetSequence = jsonWebKey.getOctetSequence();
        String encode = Base64Url.encode(octetSequence);
        return String.format(template, encode);
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
        return "Authorization successful, check your cookie";
    }
}
