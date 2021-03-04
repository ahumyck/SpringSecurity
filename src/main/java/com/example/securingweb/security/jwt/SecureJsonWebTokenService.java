package com.example.securingweb.security.jwt;

import com.example.securingweb.security.jku.JkuService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
public class SecureJsonWebTokenService implements JsonWebTokenService {

    private final static Integer DURATION = 10080; //7 days
    private final static Integer ACTIVE_BEFORE = 2;

    public static final byte[] SECRET_BYTES = ("JavaSuperPuperMasterPleaseSignMyJsonWebTokenOkay" +
            "ThisStringWasOnly352bitsAndNowIwantItToBeMoreThan512bits").getBytes(StandardCharsets.UTF_8);

    private JkuService jkuService;

    public SecureJsonWebTokenService(JkuService jkuService) {
        this.jkuService = jkuService;
    }

    @SneakyThrows
    public JsonWebToken generateToken(String username) {
        JwtClaims claims = new JwtClaims();
        claims.setClaim("jku", jkuService.getDefaultJku());
        claims.setExpirationTimeMinutesInTheFuture(DURATION); // time when the token will expire (7 days from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(ACTIVE_BEFORE); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(username); // the subject/principal is whom the token is about
        return new JsonWebToken(signToken(claims, jkuService.getJsonWebKey(jkuService.getDefaultJku()).getJsonWebKeys().get(0)), DURATION);
    }

    @SneakyThrows
    private String signToken(JwtClaims claims, JsonWebKey jsonWebKey) {
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(jsonWebKey.getKey());
        jws.setKeyIdHeaderValue(jsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(jsonWebKey.getAlgorithm());
        return jws.getCompactSerialization();
    }

    @SneakyThrows
    public String validateTokenAndGetUsername(String javaWebToken) {

        // Build a JwtConsumer that doesn't check signatures or do any validation.
        String jku = extractJku(javaWebToken);

        HttpsJwks httpsJkws = new HttpsJwks(jku);

        // The HttpsJwksVerificationKeyResolver uses JWKs obtained from the HttpsJwks and will select the
        // most appropriate one to use for verification based on the Key ID and other factors provided
        // in the header of the JWS/JWT.
        HttpsJwksVerificationKeyResolver httpsJwksKeyResolver = new HttpsJwksVerificationKeyResolver(httpsJkws);

        // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
        // be used to validate and process the JWT.
        // The specific validation requirements for a JWT are context dependent, however,
        // it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
        // and audience that identifies your system as the intended recipient.
        // If the JWT is encrypted too, you need only provide a decryption key or
        // decryption key resolver to the builder.
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setVerificationKeyResolver(httpsJwksKeyResolver)
//                .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
                .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                        AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.HMAC_SHA512) // which is only RS256 here
                .build(); // create the JwtConsumer instance

        try
        {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(javaWebToken);
            System.out.println("JWT validation succeeded! " + jwtClaims);
        }
        catch (InvalidJwtException e)
        {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
            System.out.println("Invalid JWT! " + e);

            // Programmatic access to (some) specific reasons for JWT invalidity is also possible
            // should you want different error handling behavior for certain conditions.

            // Whether or not the JWT has expired being one common reason for invalidity
            if (e.hasExpired())
            {
                System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
            }

            // Or maybe the audience was invalid
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID))
            {
                System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
            }
        }

        return "user";
    }


    @SneakyThrows
    private String extractJku(String javaWebToken) {
        JwtConsumer firstPassJwtConsumer = new JwtConsumerBuilder()
                .setSkipAllValidators()
                .setDisableRequireSignature()
                .setSkipSignatureVerification()
                .build();
        JwtClaims firstPassedJwt = firstPassJwtConsumer.processToClaims(javaWebToken);
        return (String) firstPassedJwt.getClaimValue("jku");
    }

}
