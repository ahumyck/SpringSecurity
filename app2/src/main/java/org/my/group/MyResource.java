package org.my.group;

import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.keys.HmacKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;


@Path("/secret")
public class MyResource {

    private static final Logger log = LoggerFactory.getLogger(MyResource.class);

    private static final byte[] SECRET_BYTES = ("JavaSuperPuperMasterPleaseSignMyJsonWebTokenOkay" +
            "ThisStringWasOnly352bitsAndNowIwantItToBeMoreThan512bits").getBytes(StandardCharsets.UTF_8);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String secret() {
        OctetSequenceJsonWebKey jsonWebKey = new OctetSequenceJsonWebKey(new HmacKey(SECRET_BYTES));
        jsonWebKey.setAlgorithm(AlgorithmIdentifiers.HMAC_SHA512);
        jsonWebKey.setKeyId(AlgorithmIdentifiers.HMAC_SHA512);
        JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(jsonWebKey);
        log.info("/secret endpoint return: " + jsonWebKeySet.toJson());
        return jsonWebKeySet.toJson();
    }
}