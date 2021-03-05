package com.ctf.jwtjku.controllers;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.keys.HmacKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;


@Slf4j
@RestController
public class JkuController {

    private static final byte[] SECRET_BYTES = ("JavaSuperPuperMasterPleaseSignMyJsonWebTokenOkay" +
            "ThisStringWasOnly352bitsAndNowIwantItToBeMoreThan512bits").getBytes(StandardCharsets.UTF_8);

    //todo just for test, need impl in future
    @GetMapping(value = "/secret")
    public String secret() {
        OctetSequenceJsonWebKey jsonWebKey = new OctetSequenceJsonWebKey(new HmacKey(SECRET_BYTES));
        jsonWebKey.setAlgorithm(AlgorithmIdentifiers.HMAC_SHA512);
        jsonWebKey.setKeyId(AlgorithmIdentifiers.HMAC_SHA512);
        JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(jsonWebKey);
        return jsonWebKeySet.toJson();

    }

    //todo just for test, need remove in future
    @GetMapping(value = "/vzlomjopi")
    public String vzlom() {
        return Base64.encode("secret".getBytes(StandardCharsets.UTF_8)); //c2VjcmV0
    }
}
