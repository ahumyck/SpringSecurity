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

	public Cookie generateToken(String username) {
		String tokenValue = Jwts.
				builder().
				setIssuedAt(new Date()).
				setSubject(username).
				setExpiration(Date.from(LocalDate.now().plusDays(DURATION).atStartOfDay(ZoneId.systemDefault()).toInstant())).
				signWith(SignatureAlgorithm.HS512, jwtSecret).
				compact();
		return new Cookie(tokenValue);
	}

	public String validateTokenAndGetUsername(Cookie cookie) {
		Jws<Claims> claimsJws;
		try {
			claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(cookie.getValue());
		}
		catch (Exception e) {
			log.error("Error while validating token = " + cookie.getValue());
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
