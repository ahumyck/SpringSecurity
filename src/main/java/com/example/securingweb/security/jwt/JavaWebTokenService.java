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
		String tokenValue = Jwts.
				builder().
				setSubject(username).
				setExpiration(Date.from(LocalDate.now().plusDays(DURATION).atStartOfDay(ZoneId.systemDefault()).toInstant())).
				signWith(SignatureAlgorithm.HS512, jwtSecret).
				compact();
		return new JavaWebToken(tokenValue);
	}

	public String validateTokenAndGetUsername(String javaWebToken) {
		Jws<Claims> claimsJws;
		try {
			claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(javaWebToken);
		}
		catch (Exception e) {
			log.error("Error while validating token = " + javaWebToken);
			log.error(e.getMessage());
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
