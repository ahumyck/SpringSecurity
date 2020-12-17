package com.example.securingweb.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class JavaWebTokenService {


	@Value("$(jwt.secret)") //TODO: AWS Secret Manager
	private String jwtSecret;

	private final static long DURATION = 15; //days

	public JavaWebToken generateToken(String username) {
		Date expirationDate = Date.from(LocalDate.now().plusDays(DURATION).atStartOfDay(ZoneId.systemDefault()).toInstant());
		String tokenValue = Jwts.
				builder().
				setIssuedAt(new Date()).
				setSubject(username).
				setExpiration(expirationDate).
				signWith(SignatureAlgorithm.HS512, jwtSecret).
				compact();
		return new JavaWebToken(tokenValue, expirationDate, DURATION);
	}

	public String validateTokenAndGetUsername(String token) {
		Jws<Claims> claimsJws;
		try {
			claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		}
		catch (Exception e) {
			log.error("Error while validating token = " + token);
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}

		Claims body = claimsJws.getBody();
		Date expiration = body.getExpiration();
		Date issuedAt = body.getIssuedAt();
		long diff = TimeUnit.DAYS.convert(Math.abs(expiration.getTime() - issuedAt.getTime()), TimeUnit.MILLISECONDS);
		log.info("issued at: " + issuedAt);
		log.info("expiration: " + issuedAt);
		log.info("diff: " + diff);


		if (diff > DURATION) {
			throw new RuntimeException("token has expired, re-login");
		}
		return body.getSubject();
	}

}
