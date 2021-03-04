package com.example.securingweb.security.jwt;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.base64url.Base64Url;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class SecureJsonWebTokenService {

    private final static Integer DURATION = 10080; //7 days
    private final static Integer ACTIVE_BEFORE = 2;

    public static final byte[] SECRET_BYTES = ("JavaSuperPuperMasterPleaseSignMyJsonWebTokenOkay" +
            "ThisStringWasOnly352bitsAndNowIwantItToBeMoreThan512bits").getBytes(StandardCharsets.UTF_8);

    @SneakyThrows
    public JsonWebToken generateToken(String username) {
        JwtClaims claims = new JwtClaims();
        claims.setClaim("jku", "localhost");
        claims.setExpirationTimeMinutesInTheFuture(DURATION); // time when the token will expire (7 days from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(ACTIVE_BEFORE); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(username); // the subject/principal is whom the token is about
        return new JsonWebToken(signToken(claims, getJsonWebKey()), DURATION);
    }

    private JsonWebKey getJsonWebKey() {
        OctetSequenceJsonWebKey jsonWebKey = new OctetSequenceJsonWebKey(new HmacKey(SECRET_BYTES));
        log.info("=> jwk: " + produceThumbprintHashInput(jsonWebKey));
        return jsonWebKey;
    }

    private String produceThumbprintHashInput(OctetSequenceJsonWebKey jsonWebKey) {
        String template = "{\"k\":\"%s\",\"kty\":\"oct\"}";
        byte[] octetSequence = jsonWebKey.getOctetSequence();
        String encode = Base64Url.encode(octetSequence);
        return String.format(template, encode);
    }

    @SneakyThrows
    private String signToken(JwtClaims claims, JsonWebKey jsonWebKey) {
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(jsonWebKey.getKey());
        jws.setKeyIdHeaderValue(jsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA512);
        return jws.getCompactSerialization();
    }

    public String validateTokenAndGetUsername(String javaWebToken) {
        return "";
    }

}
