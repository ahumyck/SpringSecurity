package com.ctf.jwtjku.controllers;


import com.ctf.jwtjku.services.PasswordService;
import com.ctf.jwtjku.services.SecureRandomPasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.keys.HmacKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.ctf.jwtjku.security.jwt.jku.JkuService.SECRET_PASSWORD_KEY;


@Slf4j
@RestController
public class JkuController {

    @Autowired
    private PasswordService passwordService;
    private String password;

    @PostConstruct
    private void initPassword() {
        password = passwordService.generatePassword();
    }

    @GetMapping(value = "/secret")
    public String secret() {
        OctetSequenceJsonWebKey jsonWebKey = new OctetSequenceJsonWebKey(new HmacKey(password.getBytes(StandardCharsets.UTF_8)));
        jsonWebKey.setAlgorithm(AlgorithmIdentifiers.HMAC_SHA256);
        jsonWebKey.setKeyId(AlgorithmIdentifiers.HMAC_SHA256);
        return jsonWebKey.toJson();

    }
}
