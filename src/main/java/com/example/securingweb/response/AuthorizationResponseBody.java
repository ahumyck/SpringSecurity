package com.example.securingweb.response;


import com.example.securingweb.security.jwt.JavaWebToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorizationResponseBody {
	private String tokenValue;
}
