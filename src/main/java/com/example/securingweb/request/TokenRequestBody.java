package com.example.securingweb.request;


import com.example.securingweb.security.jwt.Cookie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TokenRequestBody {
	private Cookie cookie;
}
