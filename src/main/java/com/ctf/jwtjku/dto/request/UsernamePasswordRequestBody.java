package com.ctf.jwtjku.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UsernamePasswordRequestBody {
	private String username;
	private String password;
}
