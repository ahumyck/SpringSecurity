package com.ctf.jwtjku.controllers;


import com.ctf.jwtjku.services.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.keys.HmacKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;


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
