package com.example.securingweb.security.jwt;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JsonWebTokenService {

    private final static Integer duration = 604800; //7 days
    //todo это упразнится потому что будем юзать jku
    public final static String JWT_SECRET = Base64.encode("javamaster".getBytes(StandardCharsets.UTF_8)); //"amF2YW1hc3Rlcg=="

    @Autowired
    private JwtConverter converter;

    public JsonWebToken generateToken(String username) {
        //todo сделать запрос на jku сервис который вернет секрет
        String token = Jwts.builder()
                .setHeaderParam("jku", "http://localhost:8080/secret")
                .setSubject(username)
                .setExpiration(Date.from(LocalDate.now().plusDays(duration).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        return new JsonWebToken(token, duration);
    }

    //todo достать jku header делать запрос на удаленный сервис(по jku url), после доставать ключ подписи с респонса и проверять подпись
    @SneakyThrows
    public String validateTokenAndGetUsername(String javaWebToken) {
        //todo refactor this shit
        Jws<Claims> claimsJws;
        String[] split = javaWebToken.split("\\.");
        String rawHeaders = split[0];

        String origValue = TextCodec.BASE64URL.decodeToString(rawHeaders);
        log.info("=> origValue = " + origValue);
        Map<String, Object> result = converter.convertResponse(origValue);
        log.info("=> ILLI log: " + result);

        //todo const
        String jkuUrl = (String) result.get("jku");
        RestTemplate restTemplate = new RestTemplate();
        //todo rename
        String forObject = restTemplate.getForObject(jkuUrl, String.class);
        log.info("=> retrived object: " + forObject);
        //
        try {
            claimsJws = Jwts.parser().setSigningKey(forObject).parseClaimsJws(javaWebToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Claims body = claimsJws.getBody();
        Date now = new Date();
        Date expiration = body.getExpiration();

        long timeDiff = now.getTime() - expiration.getTime();
        if (timeDiff > 0) {
            throw new RuntimeException("token has expired, re-login");
        } else {
            return body.getSubject();
        }
    }

}
