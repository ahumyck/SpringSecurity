package com.example.securingweb.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class JavaWebToken {
	private final String value;
	private final Date expirationDate;
	private final long duration;
}
